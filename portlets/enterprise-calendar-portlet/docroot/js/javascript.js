(function() {

var _DOT = '.',
	_EMPTY_STR = '',
	_SPACE = ' ',
	_PLUS = '+',
	_DASH = '-',
	_EMPY_STR = '',

	ACTIVE = 'active',
	ARROW = 'arrow',
	BLANK = 'blank',
	CALENDAR_LIST = 'calendar-list',
	CALENDARS = 'calendars',
	CLICK = 'click',
	COLOR = 'color',
	CONTENT_BOX = 'contentBox',
	DATA_ID = 'data-id',
	HIDDEN = 'hidden',
	HIDDEN_ITEMS = 'hiddenItems',
	HOVER = 'hover',
	ITEM = 'item',
	ITEMS = 'items',
	LABEL = 'label',
	RENDER = 'render',
	RENDERED = 'rendered',
	SEPARATOR = 'separator',
	SIMPLEMENU = 'simple-menu',
	SIMPLE_MENU = 'simpleMenu',
	STYLE = 'style',
	COLOR_CHANGE = 'colorChange',
	PALLETE = 'pallete',
	SELECTED = 'selected',
	SIMPLE_COLOR_PICKER = 'simple-color-picker',
	VISIBLE = 'visible',

	Workflow = Liferay.Workflow;


AUI.add('liferay-scheduler', function(A) {


var L = A.Lang,
	isArray = L.isArray,
	isString = L.isString,
	isBoolean = L.isBoolean,
	isObject = L.isObject,
	isDate = L.isDate,

	JSON = A.JSON,

	DateMath = A.DataType.DateMath,

	toNumber = function(val) {
		return parseInt(val, 10) || 0;
	},

	COMPANY_ID = toNumber(themeDisplay.getCompanyId()),
	USER_ID = toNumber(themeDisplay.getUserId()),
	GROUP_ID = toNumber(themeDisplay.getScopeGroupId());

	var jsonParse = function(val) {
		var jsonObj = null;

		try {
			jsonObj = JSON.parse(val);
		}
		catch(e) {
		}

		return jsonObj;
	};

var CalendarUtil = {
	PORTLET_NAMESPACE: '',
	USER_TIMEZONE_OFFSET: 0,

	dataSource: null,
	invokerURL: '/api/secure/jsonws/invoke',
	visibleCalendars: {},

	message: function(msg) {
		var instance = this;

		A.one('#' + instance.PORTLET_NAMESPACE + 'message').html(msg);
	},

	getDataSource: function() {
		var instance = this;

		var dataSource = instance.dataSource;

		if (!dataSource) {
			dataSource = new A.DataSource.IO(
				{
					source: instance.invokerURL,
					on: {
						request: function(e) {
							var callback = e.callback && e.callback.start;

							if (callback) {
								var payload = e.details[0];

								callback.apply(this, [payload, e]);
							}
						}
					}
				}
			),

			dataSource.plug(
				A.Plugin.DataSourceCache,
				{
					max: 100
				}
			);

			instance.dataSource = dataSource;
		}

		return instance.dataSource;
	},

	getStatusLabel: function(statusId) {
		var status = String.valueOf(statusId);

		if (Liferay.Workflow.STATUS_APPROVED === statusId) {
			status = Liferay.Language.get('accepted');
		}
		else if (Liferay.Workflow.STATUS_DRAFT === statusId) {
			status = Liferay.Language.get('draft');
		}
		else if (Liferay.Workflow.STATUS_PENDING === statusId) {
			status = Liferay.Language.get('pending');
		}
		else if (Liferay.Workflow.STATUS_DENIED === statusId) {
			status = Liferay.Language.get('declined');
		}

		return status;
	},

	destroyEvent: function(evt) {
		var instance = this;

		var scheduler = evt.get('scheduler');

		scheduler.removeEvent(evt);
		scheduler.syncEventsUI();
	},

	getEvents: function(startDate, endDate, status, success, failure) {
		var instance = this;

		var calendarIds = A.Object.keys(CalendarUtil.visibleCalendars);

		instance.invoke(
			{
				"$booking = /enterprise-calendar-portlet/calendarbooking/search": {
					calendarIds: calendarIds.join(','),
					calendarResourceIds: '',
					companyId: COMPANY_ID,
					end: -1,
					endDate: endDate.getTime(),
					groupIds: [0, GROUP_ID].join(','),
					keywords: null,
					orderByComparator: null,
					parentCalendarBookingId: -1,
					start: Workflow.STATUS_APPROVED,
					startDate: startDate.getTime(),
					status: status.join(',')
				}
			},
			{
				cache: true,
				failure: failure,
				success: success
			}
		);
	},

	getLocalizationMap: function(value) {
		var instance = this;

		var map = {};
		map[themeDisplay.getLanguageId()] = value;

		return JSON.stringify(map);
	},

	invokeTransition: function(evt, status) {
		var instance = this;

		var scheduler = evt.get('scheduler');

		instance.invoke(
			{
				"/enterprise-calendar-portlet/calendarbooking/invoke-transition": {
					calendarBookingId: evt.get('calendarBookingId'),
					transitionName: CalendarUtil.getStatusLabel(status).toLowerCase(),
					userId: USER_ID
				}
			},
			{
				success: function(data) {
					evt.set('loading', false);

					if (data) {
						if (data.exception) {
							return;
						}

						if (scheduler) {
							var eventRecorder = scheduler.get('eventRecorder');

							eventRecorder.hideOverlay();

							scheduler.loadCalendarBookings();
						}
					}
				},

				start: function() {
					evt.set('loading', true);
				}
			}
		);
	},

	addEvent: function(evt) {
		var instance = this;

		instance.invoke(
			{
				"/enterprise-calendar-portlet/calendarbooking/add-calendar-booking": {
					allDay: evt.get('allDay'),
					calendarId: evt.get('calendarId'),
					descriptionMap: instance.getLocalizationMap(evt.get('description')),
					endDate: CalendarUtil.toUTCTimeZone(evt.get('endDate')).getTime(),
					firstReminder: 0,
					location: evt.get('location'),
					parentCalendarBookingId: evt.get('parentCalendarBookingId'),
					recurrence: evt.get('repeat'),
					secondReminder: 0,
					startDate: CalendarUtil.toUTCTimeZone(evt.get('startDate')).getTime(),
					titleMap: instance.getLocalizationMap(evt.get('content')),
					userId: USER_ID
				}
			},
			{
				success: function(data) {
					evt.set('loading', false);

					if (data) {
						if (data.exception) {
							CalendarUtil.destroyEvent(evt);
							return;
						}

						evt.set('calendarBookingId', data.calendarBookingId);
						evt.set('calendarResourceId', data.calendarResourceId);
						evt.set('parentCalendarBookingId', data.parentCalendarBookingId);
						evt.set('status', data.status);

						CalendarUtil.visibleCalendars[evt.get('calendarId')].addEvent(evt);
					}
				},

				failure: function() {
					CalendarUtil.destroyEvent(evt);
				},

				start: function() {
					evt.set('loading', true);
				}
			}
		);
	},

	collectCalendarEvents: function(calendarBookings, calendarId) {
		var instance = this;

		var events = [];

		A.Array.each(calendarBookings, function(calendarBooking) {
			if (calendarId === calendarBooking.calendarId) {
				events.push(instance.toSchedulerEvent(calendarBooking));
			}
		});

		return events;
	},

	createCalendarListAutoComplete: function(resourceURL, calendarList, input) {
		var instance = this;

		input.plug(
			A.Plugin.AutoComplete,
			{
				activateFirstItem: true,
				after: {
					select: function(event) {
						calendarList.add(event.result.raw);

						input.val(_EMPY_STR);
					}
				},
				maxResults: 20,
				requestTemplate: '&' + Liferay.CalendarUtil.PORTLET_NAMESPACE + 'keywords={query}',
				resultFilters: function(query, results) {
					return A.Array.filter(
						results,
						function(item) {
							return !instance.visibleCalendars[item.raw.calendarId];
						}
					);
				},
				resultHighlighter: 'wordMatch',
				resultTextLocator: 'name',
				source: resourceURL
			}
		);
	},

	deleteEvent: function(evt) {
		var instance = this;

		instance.invoke(
			{
				'/enterprise-calendar-portlet/calendarbooking/delete-calendar-booking': {
					calendarBookingId: evt.get('calendarBookingId')
				}
			},
			{
				success: function() {
					evt.get('scheduler').loadCalendarBookings();
				}
			}
		);
	},

	invoke: function(service, callback) {
		var instance = this;

		var dataSource = instance.getDataSource();

		callback = A.merge(
			{
				cache: false
			},
			callback
		);

		if (callback.cache === false) {
			dataSource.cache.flush();
		}
console.log(service);
		dataSource.sendRequest({
			request: '?cmd=' + JSON.stringify(service),
			callback: {
				failure: callback.failure,
				start: callback.start,

				success: function(e) {
					var xhr = e && e.response.results && e.response.results[0];

					if (xhr && callback.success) {
						var data = jsonParse(xhr.responseText);

						callback.success.apply(this, [data, e]);
					}
				}
			}
		});
	},

	toUserTimeZone: function(utc) {
		var instance = this;

		if (!isDate(utc)) {
			utc = new Date(utc);
		}

		return DateMath.add(utc, DateMath.MINUTES, utc.getTimezoneOffset() + instance.USER_TIMEZONE_OFFSET/DateMath.ONE_MINUTE_MS);
	},

	toUTCTimeZone: function(date) {
		var instance = this;

		if (!isDate(date)) {
			date = new Date(date);
		}

		return DateMath.subtract(date, DateMath.MINUTES, date.getTimezoneOffset());
	},

	toSchedulerEvent: function(calendarBooking) {
		var instance = this;

		return new Liferay.SchedulerEvent({
			allDay: calendarBooking.allDay,
			calendarBookingId: calendarBooking.calendarBookingId,
			calendarId: calendarBooking.calendarId,
			content: calendarBooking.titleCurrentValue,
			description: calendarBooking.descriptionCurrentValue,
			endDate: CalendarUtil.toUserTimeZone(calendarBooking.endDate),
			location: calendarBooking.location,
			parentCalendarBookingId: calendarBooking.parentCalendarBookingId,
			startDate: CalendarUtil.toUserTimeZone(calendarBooking.startDate),
			status: calendarBooking.status
		});
	},

	syncVisibleCalendarsMap: function() {
		var instance = this;

		var visibleCalendars = instance.visibleCalendars = {};

		A.Array.each(arguments, function(calendarList) {
			var calendars = calendarList.get('calendars');

			A.each(calendars, function(calendar, key) {
				visibleCalendars[calendar.get('calendarId')] = calendar;
			});
		});

		return visibleCalendars;
	},

	updateEvent: function(evt) {
		var instance = this;

		instance.invoke(
			{
				"/enterprise-calendar-portlet/calendarbooking/update-calendar-booking": {
					allDay: evt.get('allDay'),
					calendarBookingId: evt.get('calendarBookingId'),
					calendarId: evt.get('calendarId'),
					descriptionMap: instance.getLocalizationMap(evt.get('description')),
					endDate: CalendarUtil.toUTCTimeZone(evt.get('endDate')).getTime(),
					firstReminder: 0,
					location: evt.get('location'),
					recurrence: evt.get('repeat'),
					secondReminder: 0,
					startDate: CalendarUtil.toUTCTimeZone(evt.get('startDate')).getTime(),
					status: evt.get('status'),
					titleMap: instance.getLocalizationMap(evt.get('content')),
					userId: USER_ID
				}
			},
			{
				success: function(data) {
					evt.set('loading', false);

					if (data) {
						if (data.exception) {
							// CalendarUtil.restoreEvent(evt);
							return;
						}
					}
				},

				start: function() {
					evt.set('loading', true);
				}
			}
		);
	}

};

Liferay.CalendarUtil = CalendarUtil;








var Scheduler = A.Component.create(
	{
		NAME: 'scheduler-base',

		ATTRS: {
			currentMonth: {
				setter: toNumber,
				valueFn: function(val) {
					var instance = this;

					return instance.get('currentDate').getMonth();
				}
			},

			portletNamespace: {
				value: '',
				validator: isString
			}
		},

		EXTENDS: A.Scheduler,

		UI_ATTRS: ['currentMonth'],

		prototype: {
			bindUI: function() {
				var instance = this;

				instance.after(
					{
						'scheduler-base:currentDateChange': instance._afterCurrentDateChange,
						'scheduler-event:startDateChange': instance._afterStartDateChange
					}
				);

				instance.on(
					{
						'scheduler-event-recorder:delete': instance._onDeleteEvent,
						'scheduler-event-recorder:save': instance._onSaveEvent
					}
				);

				Scheduler.superclass.bindUI.apply(this, arguments);
			},

			loadCalendarBookings: function() {
				var instance = this;

				CalendarUtil.message(Liferay.Language.get('loading') + '...');

				var currentDate = instance.get('currentDate');
				var firstDayOfWeek = instance.get('firstDayOfWeek');

				var startDate = DateMath.getFirstDayOfWeek(DateMath.findMonthStart(currentDate), firstDayOfWeek);
				var endDate = DateMath.add(DateMath.getFirstDayOfWeek(DateMath.findMonthEnd(currentDate), firstDayOfWeek), DateMath.DAY, 7);

				CalendarUtil.getEvents(
					startDate, endDate,
					[Workflow.STATUS_APPROVED, Workflow.STATUS_PENDING],
					A.bind(instance.loadCalendarBookingsJSON, instance)
				);
			},

			loadCalendarBookingsJSON: function(calendarBookings) {
				var instance = this;

				var visibleCalendarsMap = Liferay.CalendarUtil.visibleCalendars;

				A.each(visibleCalendarsMap, function(calendar, calendarId) {
					var events = CalendarUtil.collectCalendarEvents(calendarBookings, toNumber(calendarId));

					calendar.set('events', events);
				});

				instance.set('events', A.Object.values(visibleCalendarsMap));
				instance.syncEventsUI();

				CalendarUtil.message('');
			},


			_afterCurrentDateChange: function(event) {
				var instance = this;

				var currentMonth = event.newVal.getMonth();

				if (currentMonth !== instance.get('currentMonth')) {
					instance.set('currentMonth', currentMonth);
				}
			},

			_afterStartDateChange: function(event) {
				var instance = this;

				// Make the updateEvent call async to wait all attributes to be set.
				setTimeout(function() {
					CalendarUtil.updateEvent(event.target);
				}, 0);
			},

			_onDeleteEvent: function(event) {
				var instance = this;

				var evt = event.schedulerEvent;

				if (evt.isMasterBooking() && !confirm(Liferay.Language.get('deleting-this-event-will-cancel-the-meeting-with-your-guests-would-you-like-to-delete'))) {
					event.preventDefault();
					return;
				}

				CalendarUtil.deleteEvent(evt);
			},

			_onSaveEvent: function(event) {
				var instance = this;

				CalendarUtil.addEvent(event.newSchedulerEvent);
			},

			_uiSetCurrentMonth: function(val) {
				var instance = this;

				instance.loadCalendarBookings();
			}
		}
	}
);

Liferay.Scheduler = Scheduler;



var SchedulerEvent = A.Component.create({
	NAME: 'scheduler-event',

	ATTRS: {
		allDay: {
			value: false,
			validator: isBoolean
		},

		calendarId: {
			value: 0,
			setter: toNumber
		},

		calendarBookingId: {
			value: 0,
			setter: toNumber
		},

		description: {
			value: '',
			validator: isString
		},

		loading: {
			value: false,
			validator: isBoolean
		},

		location: {
			value: '',
			validator: isString
		},

		parentCalendarBookingId: {
			value: 0,
			setter: toNumber
		},

		status: {
			value: 0,
			setter: toNumber
		}
	},

	EXTENDS: A.SchedulerEvent,

	prototype: {
		initializer: function() {
			var instance = this;

			instance._uiSetLoading(
				instance.get('loading')
			);

			instance._uiSetStatus(
				instance.get('status')
			);

			instance.on('loadingChange', instance._onLoadingChange);
			instance.on('statusChange', instance._onStatusChange);
		},

		isMasterBooking: function() {
			var instance = this;

			return instance.get('parentCalendarBookingId') === instance.get('calendarBookingId');
		},

		_onLoadingChange: function(event) {
			var instance = this;

			instance._uiSetLoading(event.newVal);
		},

		_onStatusChange: function(event) {
			var instance = this;

			instance._uiSetStatus(event.newVal);
		},

		_uiSetLoading: function(val) {
			var instance = this;

			instance.get('node').toggleClass('calendar-portlet-event-loading', val);
		},

		_uiSetStatus: function(val) {
			var instance = this;

			instance.get('node').toggleClass('calendar-portlet-event-pending', val !== Liferay.Workflow.STATUS_APPROVED);
		}
	}
});

Liferay.SchedulerEvent = SchedulerEvent;



var SchedulerEventRecorder = A.Component.create(
	{
		NAME: 'scheduler-event-recorder',

		ATTRS: {
			editCalendarBookingURL: {
				validator: isString,
				value: ''
			},

			portletNamespace: {
				value: '',
				validator: isString
			},

			status: {
				value: Liferay.Workflow.STATUS_DRAFT,
				setter: toNumber
			}
		},

		EXTENDS: A.SchedulerEventRecorder,

		prototype: {
			getEventCopy: function() {
				var instance = this;
				var newEvt = instance.get('event');

				if (!newEvt) {
					newEvt = new (instance.get('eventClass'))();
				}

				newEvt.setAttrs(instance.serializeForm());

				return newEvt;
			},

			getTemplateData: function() {
				var instance = this;

				var evt = instance.get('event') || instance;

				return A.merge(
					SchedulerEventRecorder.superclass.getTemplateData.apply(this, arguments),
					{
						status: CalendarUtil.getStatusLabel(evt.get('status'))
					}
				);
			},

			isMasterBooking: function() {
				var instance = this;

				return false;
			},

			_handleAcceptEvent: function(event) {
				var instance = this;

				var evt = instance.get('event');

				if (evt) {
					CalendarUtil.invokeTransition(evt, Liferay.Workflow.STATUS_APPROVED);
				}
			},

			_handleDeclineEvent: function(event) {
				var instance = this;

				var evt = instance.get('event');

				if (evt) {
					CalendarUtil.invokeTransition(evt, Liferay.Workflow.STATUS_DENIED);
				}
			},

			_handleEditDetailsEvent: function(event) {
				var instance = this;

				var evt = instance.get('event');
				var editCalendarBookingURL = decodeURIComponent(instance.get('editCalendarBookingURL'));
				var data = instance.serializeForm();

				data.titleCurrentValue = encodeURIComponent(data.content);
				data.startDate = CalendarUtil.toUTCTimeZone(data.startDate).getTime();
				data.endDate = CalendarUtil.toUTCTimeZone(data.endDate).getTime();

				if (evt) {
					data.calendarBookingId = evt.get('calendarBookingId');
				}

				window.location.href = A.Lang.sub(editCalendarBookingURL, data);
			},

			_onOverlayVisibleChange: function(event) {
				var instance = this;

				SchedulerEventRecorder.superclass._onOverlayVisibleChange.apply(this, arguments);

				var evt = instance.get('event');
				var overlayBB = instance.overlay.get('boundingBox');
				var portletNamespace = instance.get('portletNamespace');

				overlayBB.toggleClass('calendar-portlet-event-recorder-editing', !!evt);

				var eventRecorderCalendar = A.one('#' + portletNamespace + 'eventRecorderCalendar');

				if (eventRecorderCalendar) {
					var calendarId = CalendarUtil.DEFAULT_CALENDAR.calendarId;

					if (evt) {
						calendarId = evt.get('calendarId');
					}

					eventRecorderCalendar.val(calendarId);
				}

				instance._syncToolbarButtons(event.newVal);
			},

			_syncToolbarButtons: function(overlayVisible) {
				var instance = this;

				if (!overlayVisible) {
					return;
				}

				var evt = instance.get('event') || instance;
				var status = evt.get('status');

				var overlay = instance.overlay;
				var toolbar = instance.toolbar;

				toolbar.add(
					{
						handler: A.bind(instance._handleEditDetailsEvent, instance),
						id: 'editDetailsBtn',
						label: Liferay.Language.get('edit-details')
					}
				);

				// if (evt.isMasterBooking()) {
				// 	return;
				// }

				if (status === Liferay.Workflow.STATUS_DRAFT) {
					toolbar.remove('declineBtn');
				}

				if (status === Liferay.Workflow.STATUS_APPROVED ||
					status === Liferay.Workflow.STATUS_DRAFT) {

					toolbar.remove('acceptBtn');
				}

				if (status === Liferay.Workflow.STATUS_PENDING) {
					toolbar.add(
						{
							handler: A.bind(instance._handleAcceptEvent, instance),
							icon: 'circle-check',
							id: 'acceptBtn',
							label: Liferay.Language.get('accept')
						}
					);
				}

				if (status === Liferay.Workflow.STATUS_PENDING ||
					status === Liferay.Workflow.STATUS_APPROVED) {

					toolbar.add(
						{
							handler: A.bind(instance._handleDeclineEvent, instance),
							icon: 'circle-close',
							id: 'declineBtn',
							label: Liferay.Language.get('decline')
						}
					);
				}

				var estimatedOverlayWidth = 50 + toolbar.get('boundingBox').get('offsetWidth');

				overlay.set('width', Math.max(300, estimatedOverlayWidth));
			}
		}
	}
);

Liferay.SchedulerEventRecorder = SchedulerEventRecorder;





var Calendar = A.Component.create({
	NAME: 'scheduler-calendar',

	ATTRS: {
		calendarId: {
			value: 0,
			setter: toNumber
		},

		calendarResourceId: {
			value: 0,
			setter: toNumber
		},

		classNameId: {
			value: 0,
			setter: toNumber
		},

		classPK: {
			value: 0,
			setter: toNumber
		}
	},

	prototype: {
		_afterColorChange: function(event) {
			var instance = this;

			Calendar.superclass._afterColorChange.apply(instance, arguments);

			CalendarUtil.invoke(
				{
					"/enterprise-calendar-portlet/calendar/update-calendar-color": {
						calendarId: instance.get('calendarId'),
						color: parseInt(event.newVal.substr(1), 16)
					}
				}
			);
		}
	},

	EXTENDS: A.SchedulerCalendar
});

Liferay.SchedulerCalendar = Calendar;

}, '@VERSION@' ,{ requires: ['autocomplete', 'autocomplete-highlighters', 'aui-scheduler', 'aui-io', 'datasource-get', 'datasource-cache', 'liferay-portlet-url', 'liferay-store'] });












































AUI.add('liferay-calendar-simple-menu', function(A) {

var L = A.Lang,
	isArray = L.isArray,
	isBoolean = L.isBoolean,

	AArray = A.Array,

	getCN = A.getClassName,

	CSS_SIMPLE_MENU_ITEM = getCN(SIMPLEMENU, ITEM),
	CSS_SIMPLE_MENU_ITEM_HIDDEN = getCN(SIMPLEMENU, ITEM, HIDDEN),
	CSS_SIMPLE_MENU_SEPARATOR = getCN(SIMPLEMENU, SEPARATOR),

	TPL_SIMPLE_MENU_ITEM = '<li class="{cssClass}" data-id="{id}">{caption}</li>',

	_getItemFn = A.cached(function(id, items) {
		var item = A.Array.filter(items, function(item) {
			return item.id === id;
		});

		return item[0] && item[0].fn;
	});

var SimpleMenu = A.Component.create(
	{
		NAME: SIMPLEMENU,

		ATTRS: {
			items: {
				validator: isArray,
				value: []
			},

			hiddenItems: {
				validator: isArray,
				value: []
			},

			host: {
				value: null
			}
		},

		UI_ATTRS: [ITEMS, HIDDEN_ITEMS],

		AUGMENTS: [A.WidgetStdMod, A.WidgetPosition, A.WidgetStack, A.WidgetPositionAlign, A.WidgetPositionConstrain],

		prototype: {
			CONTENT_TEMPLATE: '<ul></ul>',

			bindUI: function() {
				var instance = this;
				var contentBox = instance.get(CONTENT_BOX);

				contentBox.delegate(CLICK, A.bind(instance._onClickItems, instance), _DOT + CSS_SIMPLE_MENU_ITEM);
			},

			renderUI: function() {
				var instance = this;

				instance._renderItems(instance.get(ITEMS));
			},

			_onClickItems: function(event) {
				var instance = this;
				var items = instance.get(ITEMS);
				var target = event.currentTarget;

				var id = target.attr(DATA_ID);
				var fn = _getItemFn(id, items);

				if (fn) {
					fn.apply(instance, arguments);
				}

				event.stopPropagation();
			},

			_renderItems: function(items) {
				var instance = this;
				var contentBox = instance.get(CONTENT_BOX);
				var hiddenItems = instance.get(HIDDEN_ITEMS);

				instance.items = A.NodeList.create();

				AArray.each(items, function(item) {
					var caption = item.caption;

					if (!item.hasOwnProperty('id')) {
						item.id = A.guid();
					}

					var id = item.id;

					var cssClass = caption === _DASH ? CSS_SIMPLE_MENU_SEPARATOR : CSS_SIMPLE_MENU_ITEM;

					if (AArray.indexOf(hiddenItems, id) > -1)  {
						cssClass += _SPACE + CSS_SIMPLE_MENU_ITEM_HIDDEN;
					}

					var li = A.Node.create(
						L.sub(TPL_SIMPLE_MENU_ITEM, {
							cssClass: cssClass,
							id: id
						})
					);

					li.setContent(caption);

					instance.items.push(li);
				});

				contentBox.setContent(instance.items);
			},

			_uiSetItems: function(val) {
				var instance = this;

				if (instance.get(RENDERED)) {
					instance._renderItems(val);
				}
			},

			_uiSetHiddenItems: function(val) {
				var instance = this;

				if (instance.get(RENDERED)) {
					instance.items.each(function(item) {
						var id = item.attr(DATA_ID);

						item.toggleClass(CSS_SIMPLE_MENU_ITEM_HIDDEN, AArray.indexOf(val, id) > -1);
					});
				}
			}
		}
	}
);

Liferay.SimpleMenu = SimpleMenu;

}, '@VERSION@' ,{ requires: ['aui-base', 'aui-template', 'widget-position', 'widget-stack', 'widget-position-align', 'widget-position-constrain', 'widget-stdmod'] });

AUI.add('liferay-calendar-list', function(A) {
var L = A.Lang,
	isArray = L.isArray,
	isString = L.isString,
	isObject = L.isObject,

	AArray = A.Array,

	getCN = A.getClassName,

	CSS_CALENDAR_LIST_ITEM = getCN(CALENDAR_LIST, ITEM),
	CSS_CALENDAR_LIST_ITEM_ACTIVE = getCN(CALENDAR_LIST, ITEM, ACTIVE),
	CSS_CALENDAR_LIST_ITEM_ARROW = getCN(CALENDAR_LIST, ITEM, ARROW),
	CSS_CALENDAR_LIST_ITEM_COLOR = getCN(CALENDAR_LIST, ITEM, COLOR),
	CSS_CALENDAR_LIST_ITEM_HOVER = getCN(CALENDAR_LIST, ITEM, HOVER),
	CSS_CALENDAR_LIST_ITEM_LABEL = getCN(CALENDAR_LIST, ITEM, LABEL),

	TPL_CALENDAR_LIST_ITEM = new A.Template(
		'<tpl for="calendars">',
			'<div class="', CSS_CALENDAR_LIST_ITEM, '">',
				'<div class="', CSS_CALENDAR_LIST_ITEM_COLOR, '" {[ parent.calendars[$i].get("visible") ? ', '\'style="background-color:\'', _PLUS, 'parent.calendars[$i].get("color")', _PLUS, '";border-color:"', _PLUS, 'parent.calendars[$i].get("color")', _PLUS, '";\\""', ' : \'', _EMPTY_STR, '\' ]}></div>',
				'<span class="', CSS_CALENDAR_LIST_ITEM_LABEL, '">{[ parent.calendars[$i].get("name") ]}</span>',
				'<div href="javascript:;" class="', CSS_CALENDAR_LIST_ITEM_ARROW, '"></div>',
			'</div>',
		'</tpl>'
	);

var CalendarList = A.Component.create(
	{
		NAME: CALENDAR_LIST,

		ATTRS: {
			calendars: {
				setter: '_setCalendars',
				validator: isArray,
				value: []
			},

			simpleMenu: {
				setter: '_setSimpleMenu',
				validator: isObject,
				value: null
			}
		},

		UI_ATTRS: [ CALENDARS ],

		prototype: {
			initializer: function() {
				var instance = this;

				instance.simpleMenu = new Liferay.SimpleMenu(instance.get(SIMPLE_MENU));
			},

			bindUI: function() {
				var instance = this;
				var contentBox = instance.get(CONTENT_BOX);

				instance.on('scheduler-calendar:colorChange', A.bind(instance._onCalendarColorChange, instance));
				instance.on('scheduler-calendar:visibleChange', A.bind(instance._onCalendarVisibleChange, instance));
				instance.on('simple-menu:visibleChange', A.bind(instance._onSimpleMenuVisibleChange, instance));

				contentBox.delegate(CLICK, A.bind(instance._onClick, instance), _DOT + CSS_CALENDAR_LIST_ITEM);

				contentBox.delegate(
					HOVER,
					A.bind(instance._onHoverOver, instance),
					A.bind(instance._onHoverOut, instance),
					_DOT + CSS_CALENDAR_LIST_ITEM
				);
			},

			renderUI: function() {
				var instance = this;

				instance._renderCalendars();

				instance.simpleMenu.render();
			},

			add: function(calendar) {
				var instance = this;

				var calendars = instance.get('calendars');

				calendars.push(calendar);
				instance.set('calendars', calendars);
			},

			clear: function() {
				var instance= this;

				instance.set(CALENDARS, []);
			},

			remove: function(calendar) {
				var instance = this;
				var calendars = instance.get('calendars');

				AArray.remove(calendars, AArray.indexOf(calendars, calendar));

				instance.set(CALENDARS, calendars);
			},

			_clearCalendarColor: function(calendar) {
				var instance = this;

				var node = instance._getCalendarNode(calendar);
				var colorNode = node.one(_DOT + CSS_CALENDAR_LIST_ITEM_COLOR);

				colorNode.setAttribute(STYLE, _EMPTY_STR);
			},

			_getCalendarByNode: function(node) {
				var instance = this;
				var calendars = instance.get(CALENDARS);

				return calendars[instance.items.indexOf(node)];
			},

			_getCalendarNode: function(calendar) {
				var instance = this;
				var calendars = instance.get(CALENDARS);

				return instance.items.item(AArray.indexOf(calendars, calendar));
			},

			_onCalendarColorChange: function(event) {
				var instance = this;
				var target = event.target;

				if (target.get(VISIBLE)) {
					instance._setCalendarColor(target, event.newVal);
				}
			},

			_onCalendarVisibleChange: function(event) {
				var instance = this;
				var target = event.target;

				if (event.newVal) {
					instance._setCalendarColor(target, target.get(COLOR));
				}
				else {
					instance._clearCalendarColor(target);
				}
			},

			_onClick: function(event) {
				var instance = this;
				var target = event.target;

				if (target.hasClass(CSS_CALENDAR_LIST_ITEM_ARROW)) {
					if (instance.activeNode) {
						instance.activeNode.removeClass(CSS_CALENDAR_LIST_ITEM_ACTIVE);
					}

					instance.activeNode = event.currentTarget;
					instance.activeItem = instance._getCalendarByNode(instance.activeNode);

					instance.activeNode.addClass(CSS_CALENDAR_LIST_ITEM_ACTIVE);

					var simpleMenu = instance.simpleMenu;

					simpleMenu.setAttrs({
						'align.node': target,
						visible: simpleMenu.get('align.node') !== target || !simpleMenu.get(VISIBLE)
					});

					event.stopPropagation();
				}
				else {
					var calendar = instance._getCalendarByNode(event.currentTarget);

					calendar.set(VISIBLE, !calendar.get(VISIBLE));
				}
			},

			_onHoverOver: function(event) {
				var instance = this;
				var target = event.currentTarget;

				var calendar = instance._getCalendarByNode(target);

				instance._setCalendarColor(calendar, calendar.get(COLOR));

				target.addClass(CSS_CALENDAR_LIST_ITEM_HOVER);
			},

			_onHoverOut: function(event) {
				var instance = this;
				var target = event.currentTarget;

				var calendar = instance._getCalendarByNode(target);

				if (!calendar.get(VISIBLE)) {
					instance._clearCalendarColor(calendar);
				}

				target.removeClass(CSS_CALENDAR_LIST_ITEM_HOVER);
			},

			_onSimpleMenuVisibleChange: function(event) {
				var instance = this;

				if (instance.activeNode && !event.newVal) {
					instance.activeNode.removeClass(CSS_CALENDAR_LIST_ITEM_ACTIVE);
				}
			},

			_renderCalendars: function() {
				var instance = this;

				instance.items = A.NodeList.create(
					TPL_CALENDAR_LIST_ITEM.parse({
						calendars: instance.get(CALENDARS)
					})
				);

				instance.get(CONTENT_BOX).setContent(instance.items);
			},

			_setCalendarColor: function(calendar, val) {
				var instance = this;

				var node = instance._getCalendarNode(calendar);
				var colorNode = node.one(_DOT + CSS_CALENDAR_LIST_ITEM_COLOR);

				colorNode.setStyles(
					{
						backgroundColor: val,
						borderColor: val
					}
				);
			},

			_setCalendars: function(val) {
				var instance = this;

				AArray.each(val, function(item, i) {
					if (!A.instanceOf(item, Liferay.SchedulerCalendar)) {
						val[i] = new Liferay.SchedulerCalendar(item);
					}

					val[i].addTarget(instance);
				});

				return val;
			},

			_setSimpleMenu: function(val) {
				var instance = this;

				return A.merge(
					{
						align: {
							points: [ A.WidgetPositionAlign.TL, A.WidgetPositionAlign.BL ]
						},
						bubbleTargets: [ instance ],
						host: instance,
						items: [],
						plugins: [ A.Plugin.OverlayAutohide ],
						visible: false,
						width: 290,
						zIndex: 500
					},
					val || {}
				);
			},

			_uiSetCalendars: function(val) {
				var instance = this;

				if (instance.get(RENDERED)) {
					instance._renderCalendars();
				}
			}
		}
	}
);

Liferay.CalendarList = CalendarList;

}, '@VERSION@' ,{ requires: ['liferay-scheduler', 'aui-template'] });

AUI.add('liferay-calendar-simple-color-picker', function(A) {
var L = A.Lang,
	isArray = L.isArray,
	isBoolean = L.isBoolean,
	isString = L.isString,

	AArray = A.Array,

	getCN = A.getClassName,

	CSS_SIMPLE_COLOR_PICKER_ITEM = getCN(SIMPLE_COLOR_PICKER, ITEM),
	CSS_SIMPLE_COLOR_PICKER_ITEM_SELECTED = getCN(SIMPLE_COLOR_PICKER, ITEM, SELECTED),

	TPL_SIMPLE_COLOR_PICKER_ITEM = new A.Template(
		'<tpl for="pallete">',
			'<div class="', CSS_SIMPLE_COLOR_PICKER_ITEM, '" style="background-color: {[ parent.pallete[$i] ]}', '; border-color:', '{[ parent.pallete[$i] ]};','"></div>',
		'</tpl>'
	);

var SimpleColorPicker = A.Component.create(
	{
		NAME: SIMPLE_COLOR_PICKER,

		ATTRS: {
			color: {
				validator: isString,
				value: _EMPY_STR
			},

			host: {
				value: null
			},

			pallete: {
				setter: AArray,
				validator: isArray,
				value: ['#d96666', '#e67399', '#b373b3', '#8c66d9', '#668cb3', '#668cd9', '#59bfb3', '#65ad89', '#4cb052', '#8cbf40', '#bfbf4d', '#e0c240', '#f2a640', '#e6804d', '#be9494', '#a992a9', '#8997a5', '#94a2be', '#85aaa5', '#a7a77d', '#c4a883', '#c7561e', '#b5515d', '#c244ab']
			}
		},

		UI_ATTRS: [ COLOR, PALLETE ],

		prototype: {
			bindUI: function() {
				var instance = this;
				var contentBox = instance.get(CONTENT_BOX);

				contentBox.delegate(CLICK, A.bind(instance._onClickColor, instance), _DOT + CSS_SIMPLE_COLOR_PICKER_ITEM);
			},

			renderUI: function() {
				var instance = this;

				instance._renderPallete();
			},

			_onClickColor: function(event) {
				var instance = this;
				var pallete = instance.get(PALLETE);

				instance.set(COLOR, pallete[instance.items.indexOf(event.currentTarget)]);
			},

			_renderPallete: function() {
				var instance = this;

				instance.items = A.NodeList.create(
					TPL_SIMPLE_COLOR_PICKER_ITEM.parse({
						pallete: instance.get(PALLETE)
					})
				);

				instance.get(CONTENT_BOX).setContent(instance.items);
			},

			_uiSetColor: function(val) {
				var instance = this;
				var pallete = instance.get(PALLETE);

				instance.items.removeClass(CSS_SIMPLE_COLOR_PICKER_ITEM_SELECTED);

				var newNode = instance.items.item(pallete.indexOf(val));

				if (newNode) {
					newNode.addClass(CSS_SIMPLE_COLOR_PICKER_ITEM_SELECTED);
				}
			},

			_uiSetPallete: function(val) {
				var instance = this;

				if (instance.get(RENDERED)) {
					instance._renderPallete();
				}
			}
		}
	}
);

Liferay.SimpleColorPicker = SimpleColorPicker;

}, '@VERSION@' ,{ requires: ['aui-base', 'aui-template'] });

})();
