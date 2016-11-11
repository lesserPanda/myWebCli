// POJO
$(function(){
	// 字段
	window.BoneField = Backbone.Model.extend({
		defaults: {
			type: 'VARCHAR', // 数据库字段类型,
			uitype: 'noset', // UI组件的类型{text, textarea, radio, checkbox, select}
			//name: '', // 数据库字段名称
			//comments: '', // 数据库字段注释
			description: '', // 描述,
			size:10
		}
	});
	
	// 单元格
	window.BoneTd = Backbone.Model.extend({
		defaults: {
			show: true,
			rowspan: 1,
			colspan: 1, 
			clazz: 'drop', // 类名
			text: '',
			field: (new BoneField).toJSON()
		}
	});
	
	// 单元格集合
	window.BoneTds = Backbone.Collection.extend({
		mode: BoneTd
	});
	
	// 行
	window.BoneTr = Backbone.Model.extend({
		defaults: {
			tds: null // td数组
		}
	});
	
	// 行集合
	window.BoneTrs = Backbone.Collection.extend({
		mode: BoneTr
	});
	
	// 表格
	window.BoneTable = Backbone.Model.extend({
		defaults: {
			rows: 10,
			cols: 4,
			trs: null	// tr数组
		}
	});
	
	// 表单
	window.BoneForm = Backbone.Model.extend({
		defaults: {
			name: 'CF_', // 数据库表名
			comments: '表单名称', // 表单上的名称，即数据库表注释
			description: '表单描述', // 描述
			table: null
		},
		// 初始化一个2行2列的表格
		initialize: function(){
			var boneTds;
			var boneTr;
			var boneTrs = new BoneTrs;
			for(var i = 0; i < 4 ; i++){
				boneTds = new BoneTds;
				for(var j = 0; j < 4 ; j++){
					boneTds.add((new BoneTd).toJSON());
				}
				boneTr = new BoneTr;
				boneTr.set('tds', boneTds.toJSON());
				boneTrs.add(boneTr.toJSON());
			}
			
			var boneTable = new BoneTable;
			boneTable.set('trs', boneTrs.toJSON());
			this.set('table', boneTable.toJSON());
			$.newTr(3);
		}
	});
});

// 工具类
$(function(){
	$.extend({
		// 保持Backbone与Vue之间数据的同步
		syncBoneVue: function($this, $model) {
			$this.$data = $model.toJSON();
			Object.keys($this.$data).forEach(function (key) {
				$this.$watch(key, function (val) {
					$model.set(key, val)
	            })
	            $model.on('change:' + key, function ($model, value) {
	            	$this[key] = value
	            })
	        });
		},
		// 下一行对应的TD
		downTd: function(td) {
			var rowspan = $.attrValue(td, 'rowspan');
			return td.parent().nextAll().eq(rowspan - 1).children().eq(td.index());
		},
		// 向右对应的TD
		nextId: function(td) {
			var colspan = $.attrValue(td, 'colspan');
			return td.nextAll().eq(colspan - 1);
		},
		// 取得td colspan或rowspan的值
		attrValue: function(td, pan){
			var value = td.attr(pan);
			if (typeof(value) == 'undefined') {
				value = 1;
			} else {
				value = parseInt(value);
			}
			return value;
		},
		// 合并TD
		mergeTd: function(trs, curTd, td2, pan) {
			trs[td2.parent().index()].tds[td2.index()].show = false;
			trs[td2.parent().index()].tds[td2.index()].field = (new BoneField).toJSON();
			curTd[pan] += 1;
		},
		// 判断两个TD是否可以合并
		isMergeId: function(td1, td2, pan) {
			if (td1.length == 0 || td2.length == 0) {
				return false;
			}
			if(td2.is(':hidden')){
				return false;
			}
			return $.attrValue(td1, pan) == $.attrValue(td2, pan);
		},
		// 插入一行
		insertTr: function(trs, i) {
			var cols = trs[0].tds.length;
			var tr = $.newTr(cols);
			trs.splice(i, 0, tr)
			// 整理行
			for(var y = 0; y < cols ; y++){				
				var x = i;
				while (trs.length > x) {
					if($.hideType(trs, x+1, y) == 'rowspan'){								
						$.combTableTds(trs[x].tds[y], trs[x+1].tds[y]);
					}else{
						break;
					}
					x++;
				}
			}
		},
		// 插入一列
		insertTd: function(trs, i) {
			$.each(trs, function(j, tr) {
				tr.tds.splice(i, 0, (new BoneTd).toJSON())
			});
			// 整理列
			$.each(trs, function(j, tr) {
				var x = i;
				while (tr.tds.length > x) {
					if($.hideType(trs, j, x+1) == 'colspan'){						
						$.combTableTds(tr.tds[x], tr.tds[x + 1]);
					}else{
						break;
					}
					x++;
				}
			});
		},
		// 合理处理td的相关属性，使之排列合理
		combTableTds: function(td1, td2) {
			if(typeof(td1) != 'undefined' && typeof(td2) != 'undefined'){
				td1.rowspan = td2.rowspan;
				td1.colspan = td2.colspan;
				td1.show = false;
				td2.show = true;
				td2.rowspan = 1;
				td2.colspan = 1;
			}
		},
		// 隐藏的类型
		hideType: function(trs, x, y) {
			if(typeof(trs[x].tds[y]) == 'undefined' || trs[x].tds[y].show){
				return "erro";
			}
			var temp1 = y - 2;
			var colspan = 2;
			while (temp1 >= 0) {
				if (trs[x].tds[temp1].colspan >= colspan) {
					return 'colspan';
				}
				temp1--;
				colspan++;
			}
			
			var temp2 = x - 2;
			var rowspan = 2;
			while (temp2 >= 0) {
				if (trs[temp2].tds[y].rowspan >= rowspan) {
					return 'rowspan';
				}
				temp2--;
				rowspan++;
			}
			return "erro";
		},
		// 取得新的TR，
		// cols 表示列数
		newTr: function(cols) {
			var boneTds = new BoneTds;
			for(var j = 0; j < cols ; j++){
				boneTds.add((new BoneTd).toJSON());
			}
			var boneTr = new BoneTr;
			boneTr.set('tds', boneTds.toJSON());
			return boneTr.toJSON();
		},
		// 取得表单的HTML代码
		getFormbuilderHtml: function() {
			var clone = $("#designTable").clone();
			clone.find(".save-form").remove();
			clone.find(".quanpingBt").remove();
			clone.find(".cover").remove();
			clone.find(".editing").removeClass("editing");
	    	return clone.html();
		},
		// 取得正确的数字长度
		rightLenth: function(field) {
			if (field.uitype == 'radio' || field.uitype == 'checkbox' || field.uitype == 'select') {
				return 100;
			}
			if (field.uitype == 'file') {
				return 200;
			}
			if(field.uitype =='text' || field.uitype == 'textarea'){
				return field.max;
			}
			return 0;
		},
		// 判断这行或列是否可以删除
		canRemoveTd: function(td, pan) {
			var bool = true;
			if (pan == 'rowspan') {
				td.parent().find('td').each(function(i) {
					if($(this).is(':hidden') || $.attrValue($(this), 'colspan') != 1 || $.attrValue($(this), 'rowspan') != 1){
						bool = false;
						return false;
					}
				});
			}else{
				var pi = td.index();
				td.parent().parent().find('tr').each(function(i){
					var td = $(this).find('td').eq(pi);
					if(td.is(':hidden') || $.attrValue(td, 'colspan') != 1 || $.attrValue(td, 'rowspan') != 1){
						bool = false;
						return false;
					}
				});
			}
			return bool;
		},
		// 删除这行或列
		removeTd: function(trs, td, pan) {
			var i = td.index();
			var j = td.parent().index();
			if (pan == 'rowspan') {
				trs.splice(j, 1);
			}else{
				$.each(trs, function(p, tr) {
					tr.tds.splice(i, 1);
				});
			}
		},
		// 取得选中的值
		getRightSelectVae: function(options, multiple) {
			var selected = [];
			$.each(options, function(i, option){
				if (option.selected) {
					selected.push(option.label);
				}
			});
			if (multiple) {
				return selected;
			}else{
				return selected.length > 0 ? selected[0] : "";
			}
		},
		// 处理菜单的可用状态
		combMenuStatus: function(td, downTd, nextId) {
			// 删除UI
			$.fntMenuStatus(td.find("div.fb-field-wrapper").size() > 0, 'clean-ui');
			// 合并状态的判断
			var b1 = $.isMergeId(td, downTd, 'colspan');
			var b2 = $.isMergeId(td, nextId, 'rowspan');
			$.fntMenuStatus(b1 || b2, 'merge-menu');
			$.fntMenuStatus(b1, 'merge-down');
			$.fntMenuStatus(b2, 'merge-right');
			// 删除状态的判断
			b1 = $.canRemoveTd(td, 'rowspan');
			b2 = $.canRemoveTd(td, 'colspan');
			$.fntMenuStatus(b1 || b2, 'remove-menu');
			$.fntMenuStatus(b1, 'remove-row');
			$.fntMenuStatus(b2, 'remove-col');
		},
		// 简化操作
		fntMenuStatus: function(bool, id) {
			if (bool) {
				$('#smartmenu').menu('enableItem', $('#'+id)[0]);
			} else {
				$('#smartmenu').menu('disableItem', $('#'+id)[0]);
			}
		},
		// 判断这这个快捷键是否可以执行
		eventQuickKey: function(number, id, event) {
			var bool = false;
			if ( event.which == number ){				
				var itemEl = $('#' + id);
				var item = $('#smartmenu').menu('getItem', itemEl[0]);
				bool = !item.disabled && !itemEl.is(':hidden');
			}
			if (bool) {
				$('#smartmenu').menu('hide');
			}
			return bool;
		}
	});
	
});


$(function(){
	// 字段显示组件
	Vue.component('edit-component-noset',{
		replace: true,
		template: ''
	});
	
	// 字段设置组件
	Vue.component('view-component-noset',{
		replace: true,
		template: ''
	});
	
	// 表单整体属性设置
	window.FormDesign = new Backbone.Model({
		basic: [],
		common: [],
		views: {},
		edits: {},
		datas: {}
	});
	
	// 数据过滤器
	Vue.filter('uppercase', {
		write : function(val, oldVal) {
			if(val == '')
				return '';
			else
				return /^[a-zA-Z][a-zA-Z0-9_]*$/.test(val) ? val.toUpperCase() : oldVal;
		}
	});
	
	// 数据过滤器
	Vue.filter('prefix', {
		write : function(val, oldVal) {
			if(val == 'CF' || val == 'F_' || val == 'C_'){
				return 'CF_';
			}
			if(val.indexOf('CF_') != 0){
				val = 'CF_' + val
			}
			return val;
		}
	});
	// 数据过滤器
	Vue.filter('number', {
		write : function(val, oldVal) {
			if (val == '') {
				return '';
			}
			if (!isNaN(val)) {				
				return parseInt(val);
			} else {
				return oldVal;
			}
		}
	});
	
	// 字段注册方法
	FormDesign.regBasicField = function(type, data) {
		FormDesign.get('basic').push({
			type: type,
			name: data.name
		});
		// 字段显示组件
		Vue.component('edit-component-' + type, {
			replace: true,
			template: data.edit,
			methods: {
				requireFnt: function(e){
					this.field.required = !this.field.required;
				},
				searchFnt: function(e){
					this.field.search = !this.field.search;
				},
				inlistFnt: function(e){
					this.field.inlist = !this.field.inlist;
				},
				radioFnt: function($this) {
					if($this.$event.pageX < $($this.$el).offset().left + 20){
						$this.checked = !$($this.$el).hasClass('fieldCheckHt');
						var $index = $('.fieldSelectLists').index($($this.$el));
						if($this.checked){
							$.each(this.field.options, function(i, option){
								if($index != i){								
									option.checked = false;
								}
							});
						}
					}					
				},
				checkboxFnt: function($this) {
					if($this.$event.pageX < $($this.$el).offset().left + 20){
						$this.checked = !$($this.$el).hasClass('fieldCheckHt');
					}					
				},
				spanFnt: function($this) {
					var $index = $('.fieldSelectLists').index($($this.$el));
					this.field.options.splice($index, 1);
				},
				spanStFnt: function($this) {
					var $index = $('.fieldSelectLists').index($($this.$el)) - 1;
					this.field.options.splice($index, 1);
					this.field.selectEle = $.getRightSelectVae(this.field.options, this.field.multiple);
				},
				addOption: function() {
					this.field.options.push({
						label: '',
						checked: false
					});
				},
				addSelectOption: function() {
					this.field.options.push({
						label: null,
						selected: false
					});
				},
				multipleFnt: function() {
					this.field.multiple = !this.field.multiple;
					this.field.options = [{label:'', selected:false}, {label:'', selected:false}];
				},
				selectFnt: function($this) {
					if($this.$event.pageX < $($this.$el).offset().left + 20){
						$this.selected = !$this.selected;
						var $index = $('.fieldSelectLists').index($($this.$el)) - 1;
						if($this.selected && !this.field.multiple){
							$.each(this.field.options, function(i, option){
								if($index != i){								
									option.selected = false;
								}
							});
						}
						this.field.selectEle = $.getRightSelectVae(this.field.options, this.field.multiple);
					}
				}
			}
		});
		// 字段设置组件
		Vue.component('view-component-' + type, {
			replace: true,
			template: data.view,
		});
		data.data.uitype = type,
		(FormDesign.get('datas'))[type] = data.data;
	};
	
	// 字段注册
	FormDesign.regCommonField = function(type, data) {
		FormDesign.get('common').push({
			type: type,
			name: data.name
		});
		// 字段设置组件
		Vue.component('edit-component-' + type, {
			replace: true,
			template: data.edit,
			methods: {
				requireFnt: function(e){
					this.field.required = !this.field.required;
				},
				searchFnt: function(e){
					this.field.search = !this.field.search;
				},
				inlistFnt: function(e){
					this.field.inlist = !this.field.inlist;
				},
				format: function(e) {
					var change = $(e.target).val();
					if (change != '时间格式') {
						this.field.format = change;
						this.field.size = change.length;
					}
					$(e.target).find('option').attr('selected', false);
				}
			}
		});
		// 字段设置组件
		Vue.component('view-component-' + type, {
			replace: true,
			template: data.view
		});
		data.data.uitype = type,
		(FormDesign.get('datas'))[type] = data.data;
	};
	
	// 取得对应的data值
	FormDesign.data = function(type) {
		return (FormDesign.get('datas'))[type];
	}
});

//UI组件定义及扩展
$(function(){
	var common = '<div class="formsetUpType">'
				+ '<span>{{field.comments}}</span>'
				+ '<code class="field-type">{{field.uitype}}</code>'
				+ '</div>'
				+ '<div class="fieldSet">'
				+ '<div class="fieldSetTitle">基本设置</div>'
				+ '<div class="fieldSetWhole">'
				/*+ '<div class="fieldName"><input v-model="field.name | uppercase" placeholder="字段名称,允许英文数字及下划线"/></div>'
				+ '<div class="fieldName"><input v-model="field.comments" placeholder="字段标签"/></div>'*/

				+ '<div class="fieldPrompt"><textarea v-model="field.cols_desc" placeholder="列描述，单元格所在列的描述，多个#号隔开" ></textarea></div>'
				+ '<div class="fieldPrompt"><textarea v-model="field.rows_desc" placeholder="行描述，单元格所在行的描述，多个#号隔开" ></textarea></div>'
				
				+ '<div class="fieldPrompt"><textarea v-model="field.description" placeholder="这里添加信息提示信息" ></textarea></div>'
				+ '<a class="fieldCheck{{field.required?\' fieldCheckHt\':\'\'}}" v-on="click: requireFnt">必须的</a>'
				+ '<a class="fieldCheck{{field.search?\' fieldCheckHt\':\'\'}}" v-on="click: searchFnt">字段查询</a>'
				+ '<a class="fieldCheck{{field.inlist?\' fieldCheckHt\':\'\'}}" v-on="click: inlistFnt">字段列表</a>'
				+ '</div>'
				+ '</div>';
/*	// 注册或扩展基础组件
	FormDesign.regBasicField('text',{
		name: '单行文本',
		view: '<div class="fb-field-wrapper" data-field-type="{{field.uitype}}" data-field-name="单行文本" data-field-check="required:{{field.required}},min:{{field.min}},max:{{field.max}}" v-draggable>'
			+ '<div class="cover"></div>'
			+ '<input type="text" name="data" size="{{field.size}}"> '
			
			+ '<input type="hidden" name="columns" value="{{field.cols_desc}}"> '
			+ '<input type="hidden" name="rows" value="{{field.rows_desc}}"> '
			
			+ '<input type="hidden" name="isList" value="{{field.inlist+\'\'}}"> '
			+ '<input type="hidden" name="isQuery" value="{{field.search+\'\'}}"> '
			
			+ '<span class="help-block">{{field.description}}</span>'
			+ '</div>',
			
		edit:'<div class="fieldSelect">'
			+ common
			+ '<div class="fieldSetTitle">文本框长度设置</div>'
			+ '<input v-model="field.size | number" size="6"/>'
			+ '<div class="fieldSetTitle">字符范围设置</div>'
			+ '最小值<input v-model="field.min | number" size="6"/>'
			+ '最大值<input v-model="field.max | number" size="6"/>'
			+ '</div>',
		data:{
			type: 'VARCHAR', 
			name: '', 
			comments: '', 
			cols_desc: '',
			rows_desc: '',
			description: '', 
			required: false,
			search: false,
			inlist: false,
			size: 10,
			min: 0,
			max: 50
		}
	});
	
	FormDesign.regBasicField('textarea',{
		name: '多行文本',
		view: '<div class="fb-field-wrapper" data-field-type="{{field.uitype}}" data-field-name="多行文本" data-field-check="required:{{field.required}},min:{{field.min}},max:{{field.max}}" v-draggable>'
			+ '<div class="cover"></div>'
			+ '<textarea rows="{{field.rows}}" cols="{{field.cols}}" name="data"></textarea>'
			+ '<input type="hidden" name="columns" value="{{field.cols_desc}}"> '
			+ '<input type="hidden" name="rows" value="{{field.rows_desc}}"> '
			+ '<input type="hidden" name="isList" value="{{field.inlist+\'\'}}"> '
			+ '<input type="hidden" name="isQuery" value="{{field.search+\'\'}}"> '
			+ '<span class="help-block">{{field.description}}</span>'
			+ '</div>',
		edit:'<div class="fieldSelect">'
			+ common
			+ '<div class="fieldSetTitle">宽高设置</div>'
			+ '宽度<input v-model="field.cols | number" size="6"/>'
			+ '高度<input v-model="field.rows | number" size="6"/>'
			+ '<div class="fieldSetTitle">字符范围设置</div>'
			+ '最小值<input v-model="field.min | number" size="6"/>'
			+ '最大值<input v-model="field.max | number" size="6"/>'
			+ '</div>',
		data:{
			type: 'VARCHAR', 
			cols_desc: '',
			rows_desc: '',
			description: '',
			required: false,
			search: false,
			inlist: false,
			cols: 20,
			rows: 2,
			min: 0,
			max: 500
		}
	});
	
	FormDesign.regBasicField('radio',{
		name: '单选框',
		view: '<div class="fb-field-wrapper" data-field-type="{{field.uitype}}" data-field-name="单选框" data-field-check="required:{{field.required}}" v-draggable>'
			+ '<div class="cover"></div>'
			+ '<div v-repeat="field.options">'
			+ '<label class="fb-option">'
			+ '<input type="radio" checked="{{checked}}" name="data" value="{{label}}">{{label}}'
			+ '</label>'
			+ '</div>'
			+ '<input type="hidden" name="columns" value="{{field.cols_desc}}"> '
			+ '<input type="hidden" name="rows" value="{{field.rows_desc}}"> '
			+ '<input type="hidden" name="isList" value="{{field.inlist+\'\'}}"> '
			+ '<input type="hidden" name="isQuery" value="{{field.search+\'\'}}"> '
			+ '<span class="help-block">{{field.description}}</span>'
			+ '</div>',
		edit: '<div class="fieldSelect">'
			+ common
			+ '<div class="fieldSetTitle">选项</div>'
			+ '<div class="fieldSelectLists{{checked?\' fieldCheckHt\':\'\'}}" v-repeat="field.options" v-on="click: radioFnt(this)"><input v-model="label"><span v-on="click: spanFnt(this)"></span></div>'
			+ '<input class="fieldSelectBt" type="button" value="增加选项" v-on="click: addOption">'
			+ '</div>',
		data:{
			type: 'VARCHAR', 
			cols_desc: '',
			rows_desc: '',
			description: '', 
			required: false,
			search: false,
			inlist: false,
			options:[{
				label:'',
				checked:false
			},{
				label:'',
				checked:false
			}]
		}
	});
	
	FormDesign.regBasicField('checkbox',{
		name: '复选框',
		view: '<div class="fb-field-wrapper" data-field-type="{{field.uitype}}" data-field-name="复选框" data-field-check="required:{{field.required}}" v-draggable>'
			+ '<div class="cover"></div>'
			+ '<div v-repeat="field.options">'
			+ '<label class="fb-option">'
			+ '<input type="checkbox" checked="{{checked}}" name="data" value="{{label}}">{{label}}'
			+ '</label>'
			+ '</div>'
			+ '<input type="hidden" name="columns" value="{{field.cols_desc}}"> '
			+ '<input type="hidden" name="rows" value="{{field.rows_desc}}"> '
			+ '<input type="hidden" name="isList" value="{{field.inlist+\'\'}}"> '
			+ '<input type="hidden" name="isQuery" value="{{field.search+\'\'}}"> '
			+ '<span class="help-block">{{field.description}}</span>'
			+ '</div>',
		edit: '<div class="fieldSelect">'
			+ common
			+ '<div class="fieldSetTitle">选项</div>'
			+ '<div class="fieldSelectLists{{checked?\' fieldCheckHt\':\'\'}}" v-repeat="field.options" v-on="click: checkboxFnt(this)"><input v-model="label"><span v-on="click: spanFnt(this)"></span></div>'
			+ '<input class="fieldSelectBt" type="button" value="增加选项" v-on="click: addOption">'
			+ '</div>',
		data:{
			type: 'VARCHAR', 
			cols_desc: '',
			rows_desc: '',
			description: '', 
			required: false,
			search: false,
			inlist: false,
			options:[{
				label:'',
				checked:false
			},{
				label:'',
				checked:false
			}]
		}
	});
	
	FormDesign.regBasicField('select',{
		name: '下拉框',
		view: '<div class="fb-field-wrapper" data-field-type="{{field.uitype}}" data-field-name="下拉框" data-field-check="required:{{field.required}}" v-draggable>'
			+ '<div class="cover"></div>'
			+ '<select name="data" v-model="field.selectEle" multiple="{{field.multiple}}">'
			+ '<option v-repeat="field.options">{{label}}</option>'		
			+ '</select>'
			+ '<input type="hidden" name="columns" value="{{field.cols_desc}}"> '
			+ '<input type="hidden" name="rows" value="{{field.rows_desc}}"> '
			+ '<input type="hidden" name="isList" value="{{field.inlist+\'\'}}"> '
			+ '<input type="hidden" name="isQuery" value="{{field.search+\'\'}}"> '
			+ '<span class="help-block">{{field.description}}</span>'
			+ '</div>',
		edit: '<div class="fieldSelect">'
			+ common
			+ '<div class="fieldSetTitle">选项</div>'
			+ '<div class="fieldSelectLists{{field.multiple?\' fieldCheckHt\':\'\'}}" v-on="click: multipleFnt">多选</div>'
			+ '<div class="fieldSelectLists{{selected?\' fieldCheckHt\':\'\'}}" v-repeat="field.options" v-on="click: selectFnt(this)"><input v-model="label"><span v-on="click: spanStFnt(this)"></span></div>'
			+ '<input class="fieldSelectBt" type="button" value="增加选项" v-on="click: addSelectOption">'
			+ '</div>',
		data:{
			type: 'VARCHAR', 
			cols_desc: '',
			rows_desc: '',
			description: '', 
			required: false,
			search: false,
			inlist: false,
			multiple: true,
			options:[{
				label:'',
				selected:false
			},{
				label:'',
				selected:false
			}],
			selectEle:null
		}
	});
	*/
	FormDesign.regBasicField('number', {
		name: '数字',
		view: '<div class="fb-field-wrapper" data-field-type="{{field.uitype}}" data-field-name="数字" data-field-check="required:{{field.required}},min:{{field.min}},max:{{field.max}},digit:{{field.digit}}" v-draggable>'
			+ '<div class="cover"></div>'
			+ '<input type="text" name="data" size="{{field.size}}" />{{field.unit}}'
			+ '<input type="hidden" name="columns" value="{{field.cols_desc}}"> '
			+ '<input type="hidden" name="rows" value="{{field.rows_desc}}"> '
			+ '<input type="hidden" name="isList" value="{{field.inlist+\'\'}}"> '
			+ '<input type="hidden" name="isQuery" value="{{field.search+\'\'}}"> '
			+ '<span class="help-block">{{field.description}}</span>'
			+ '</div>',
			
		edit:'<div class="fieldSelect">'
			+ common
			+ '<div class="fieldSetTitle">文本框长度设置</div>'
			+ '<input v-model="field.size | number" size="6"/>'
			+ '<div class="fieldSetTitle">数字范围</div>'
			+ '最小值<input v-model="field.min | number" size="6"/>'
			+ '最大值<input v-model="field.max | number" size="6"/>'
			+ '<div class="fieldSetTitle">小数点后面位数</div>'
			+ '<input v-model="field.digit | number" size="6"/>'
			+ '<div class="fieldSetTitle">单位</div>'
			+ '<input v-model="field.unit" size="6"/>'
			+ '</div>',
		data:{
			type: 'NUMERIC', 
			cols_desc: '',
			rows_desc: '',
			description: '', 
			required: false,
			search: false,
			inlist: false,
			min: 0,
			max: 10000,
			size: 6,
			digit: 0,
			unit:''
		}
	});
	/*
	//.......
	FormDesign.regCommonField('time', {
		name: '时间',
		view: '<div class="fb-field-wrapper" data-field-type="{{field.uitype}}" data-field-name="时间" v-draggable>'
			+ '<div class="cover"></div>'
			+ '<input type="text" name="{{field.name}}" size="{{field.size}}" class="Wdate" onclick="WdatePicker({dateFmt:\'{{field.format}}\'});"> '
			+ '<span class="help-block">{{field.description}}</span>'
			+ '</div>',
			
		edit:'<div class="fieldSelect">'
			+ common
			+ '<div class="fieldSetTitle">文本框长度设置</div>'
			+ '<input v-model="field.size | number" size="6"/>'
			+ '<div class="fieldSetTitle">时间格式设置</div>'
			+ '<input v-model="field.format" placeholder="时间格式"/>'
			+ '<select v-on="change: format">'
			+ '	<option>时间格式</option>'
			+ '	<option>yyyy-MM-dd</option>'
			+ '	<option>yyyy-MM-dd HH:mm</option>'
			+ '	<option>yyyy-MM-dd HH:mm:ss</option>'
			+ '</select>'
			+ '</div>',
		data:{
			type: 'DATE', 
			name: 'TIME_DATE', 
			comments: '时间', 
			description: '请选择时间', 
			required: false,
			search: false,
			inlist: false,
			size: 10,
			format:'yyyy-MM-dd'
		}
	});
	
	FormDesign.regCommonField('file', {
		name: '文件上传',
		view: '<div class="fb-field-wrapper" data-field-type="{{field.uitype}}" data-field-name="文件上传" v-draggable>'
			+ '<div class="cover"></div>'
			+ '<input type="text" name="{{field.name}}" size="{{field.size}}" />{{field.unit}}'
			+ '<span class="help-block">{{field.description}}</span>'
			+ '<input type="button" value="浏览" class="uploadBtn"/>'
			+ '</div>',
			
		edit:'<div class="fieldSelect">'
			+ common
			+ '<div class="fieldSetTitle">文本框长度设置</div>'
			+ '<input v-model="field.size | number" size="6"/>'
			+ '</div>',
		data:{
			type: 'VARCHAR', 
			name: 'UPLOAD_FILE', 
			comments: '文件上传', 
			description: '请选择本地的文件上传', 
			required: false,
			search: false,
			inlist: false,
			size:20
		}
	});
	
	FormDesign.regCommonField('price', {
		name: '价格',
		view: '<div class="fb-field-wrapper" data-field-type="{{field.uitype}}" data-field-name="价格" v-draggable>'
			+ '<div class="cover"></div>'
			+ '{{field.unit}}<input type="text" name="{{field.name}}" size="{{field.size}}" />'
			+ '<span class="help-block">{{field.description}}</span>'
			+ '</div>',
			
		edit:'<div class="fieldSelect">'
			+ common
			+ '<div class="fieldSetTitle">文本框长度设置</div>'
			+ '<input v-model="field.size | number" size="6"/>'
			+ '<div class="fieldSetTitle">价格范围</div>'
			+ '最小值<input v-model="field.min | number" size="6"/>'
			+ '最大值<input v-model="field.max | number" size="6"/>'
			+ '<div class="fieldSetTitle">小数点后面位数</div>'
			+ '<input v-model="field.digit | number" size="6"/>'
			+ '<div class="fieldSetTitle">单位</div>'
			+ '<input v-model="field.unit" size="6"/>'
			+ '</div>',
		data:{
			type: 'NUMERIC', 
			name: 'PRICE', 
			comments: '价格', 
			description: '请输入价格', 
			required: true,
			search: false,
			inlist: false,
			size: 6,
			digit: 2,
			min:0,
			max:10000,
			unit:'¥'
		}
	});
	
	FormDesign.regCommonField('name',{
		name: '姓名',
		view: '<div class="fb-field-wrapper" data-field-type="{{field.uitype}}" data-field-name="姓名" v-draggable>'
			+ '<div class="cover"></div>'
			+ '<input type="text" name="{{field.name}}" size="{{field.size}}"> '
			+ '<span class="help-block">{{field.description}}</span>'
			+ '</div>',
			
		edit:'<div class="fieldSelect">'
			+ common
			+ '<div class="fieldSetTitle">文本框长度设置</div>'
			+ '<input v-model="field.size | number" size="6"/>'
			+ '<div class="fieldSetTitle">字符范围设置</div>'
			+ '最小值<input v-model="field.min | number" size="6"/>'
			+ '最大值<input v-model="field.max | number" size="6"/>'
			+ '</div>',
		data:{
			type: 'VARCHAR', 
			name: 'REAL_NAME', 
			comments: '姓名', 
			description: '请输入您的真实姓名', 
			required: false,
			search: false,
			inlist: false,
			size: 15,
			min: 0,
			max: 50
		}
	});
	
	FormDesign.regCommonField('phone',{
		name: '电话',
		view: '<div class="fb-field-wrapper" data-field-type="{{field.uitype}}" data-field-name="电话" v-draggable>'
			+ '<div class="cover"></div>'
			+ '<input type="text" name="{{field.name}}" size="{{field.size}}"> '
			+ '<span class="help-block">{{field.description}}</span>'
			+ '</div>',
			
		edit:'<div class="fieldSelect">'
			+ common
			+ '<div class="fieldSetTitle">文本框长度设置</div>'
			+ '<input v-model="field.size | number" size="6"/>'
			+ '<div class="fieldSetTitle">字符范围设置</div>'
			+ '最小值<input v-model="field.min | number" size="6"/>'
			+ '最大值<input v-model="field.max | number" size="6"/>'
			+ '</div>',
		data:{
			type: 'VARCHAR', 
			name: 'PHONE', 
			comments: '电话', 
			description: '请输入您的联系电话', 
			required: false,
			search: false,
			inlist: false,
			size: 15,
			min: 0,
			max: 20
		}
	});
	
	FormDesign.regCommonField('email',{
		name: '邮箱',
		view: '<div class="fb-field-wrapper" data-field-type="{{field.uitype}}" data-field-name="邮箱" v-draggable>'
			+ '<div class="cover"></div>'
			+ '<input type="text" name="{{field.name}}" size="{{field.size}}"> '
			+ '<span class="help-block">{{field.description}}</span>'
			+ '</div>',
			
		edit:'<div class="fieldSelect">'
			+ common
			+ '<div class="fieldSetTitle">文本框长度设置</div>'
			+ '<input v-model="field.size | number" size="6"/>'
			+ '<div class="fieldSetTitle">字符范围设置</div>'
			+ '最小值<input v-model="field.min | number" size="6"/>'
			+ '最大值<input v-model="field.max | number" size="6"/>'
			+ '</div>',
		data:{
			type: 'VARCHAR', 
			name: 'EMAIL', 
			comments: '邮箱', 
			description: '请输入您的电话邮箱', 
			required: false,
			search: false,
			inlist: false,
			size: 20,
			min: 0,
			max: 200
		}
	});*/
	

	
	
});


// Vue扩展
$(function(){
	
	// 定义可拖拽指令
	Vue.directive('draggable', {
		bind : function() {
			//待处理焦点
			//if($(this.el).is('div')){				
			//	console.log($(this.el));
			//}
			$(this.el).draggable({
				proxy: function(source){
					var p = $('<div class="draggableMove">'+$(source).attr('data-field-name')+'</div>');
					p.appendTo('body');
					return p;
				},
				delay: 300,
				revert: true
			});
		}
	});
	
	// 字段设置
	window.designField = new Vue({
		el: '#designField',
		data: {
			uitype: 'noset'
		}
	});
	// 右击菜单显示指令
	Vue.directive('smartmenu', {
		bind : function() {
			// 右击菜单显示指令
			var trs = this.vm.$root.table.trs;
			var curTd = this.vm.td;
			
			$(this.el).bind('contextmenu',function(e){
				e.preventDefault();
				var td = $(this);
				var downTd = $.downTd(td);
				var nextId = $.nextId(td);
				$('#smartmenu').menu({	
					onShow:function() {
						$(document).bind('keydown', function(event){ 
							// console.log(event.which);
							// 注册快捷键
							if(event.which == 73){
								$('#insert-menu').trigger("mouseenter");
							}
							if(event.which == 77){
								$('#merge-menu').trigger("mouseenter");
							}
							if(event.which == 68){
								$('#remove-menu').trigger("mouseenter");
							}
							if($.eventQuickKey(76, 'insert-left', event)) {
								$.insertTd(trs, td.index());
							}
							if($.eventQuickKey(82, 'insert-right', event)) {
								$.insertTd(trs, td.index() + $.attrValue(td, 'colspan'));
							}
							if($.eventQuickKey(65, 'insert-top', event)) {
								$.insertTr(trs, td.parent().index());
							}
							if($.eventQuickKey(66, 'insert-down', event)) {
								$.insertTr(trs, td.parent().index() + $.attrValue(td, 'rowspan'));
							}
							if($.eventQuickKey(82, 'merge-right', event)) {
								$.mergeTd(trs, curTd, nextId, 'colspan');
							}
							if($.eventQuickKey(66, 'merge-down', event)) {
								$.mergeTd(trs, curTd, downTd, 'rowspan');
							}
							if($.eventQuickKey(82, 'remove-row', event)) {
								$.removeTd(trs, td, 'rowspan');
							}
							if($.eventQuickKey(67, 'remove-col', event)) {
								$.removeTd(trs, td, 'colspan');
							}
						});
					},
					onHide:function() {
						$(document).unbind('keydown');
					},
					onClick: function(item) {
						switch (item.name) {
						case 'clean-ui':
							curTd.field = (new BoneField).toJSON();
							break;
						case 'insert-left':
							$.insertTd(trs, td.index());
							break;
						case 'insert-right':
							$.insertTd(trs, td.index() + $.attrValue(td, 'colspan'));
							break;
						case 'insert-top':
							$.insertTr(trs, td.parent().index());
							break;
						case 'insert-down':
							$.insertTr(trs, td.parent().index() + $.attrValue(td, 'rowspan'));
							break;
						case 'merge-right':
							$.mergeTd(trs, curTd, nextId, 'colspan');
							break;
						case 'merge-down':
							$.mergeTd(trs, curTd, downTd, 'rowspan');
							break;
						case 'remove-row':
							$.removeTd(trs, td, 'rowspan');
							break;
						case 'remove-col':
							$.removeTd(trs, td, 'colspan');
							break;
						case 'align-center':
							td.css({'text-align': 'center'});
							break;
						case 'align-left':
							td.css({'text-align': 'left'});
							break;
						case 'align-right':
							td.css({'text-align': 'right'});
							break;
						case 'table-attr':
							cosole.log('这个功能暂未实现');
							break;
						default:
							break;
						}
					}
				});
				$.combMenuStatus(td, downTd, nextId);
	            $('#smartmenu').menu('show', {
	                left: e.pageX,
	                top: e.pageY
	            });
            });
			
			// 双击编辑
			$(this.el).dblclick(function() {
				if ($(this).find("input").size() == 0) {
					$('<input class="clickEdit" value="'+curTd.text+'"/>')
					.appendTo($(this).find('label').empty())
					.focusout(function() {
						curTd.text = $(this).val();
						$(this).replaceWith(curTd.text);
					}).focus();
				}
			});
			// 可放置
			// 注册可放置的元素
			var $this = this.el;
			$(this.el).droppable({
				onDragEnter:function(){
		            $(this).addClass('over');
		        },
		        onDragLeave:function(){
		            $(this).removeClass('over');
		        },
		        onDrop:function(e,source){
		        	$(this).removeClass('over');
		        	$("#designTable td").removeClass('editing');
		        	$(this).addClass('editing');
		        	var dataFieldType = $(source).attr('data-field-type');
		        	if ($(source).is('div')) {// 单元格之间元素的移动
		        		// 取得源TD的相关属性值
		        		var sourceTd = trs[$(source).parent().parent().index()].tds[$(source).parent().index()];
		        		// 复制源TD的相关属性值
		        		var sourceCloneTd = $.extend(true, {}, sourceTd.field);
		        		// 删除源TD的相关属性值
		        		sourceTd.field = (new BoneField).toJSON();
		        		// 当前的TD字段值初始化
		        		curTd.field = sourceCloneTd;
		        	} else {
		        		curTd.field = $.extend(true, {}, FormDesign.data(dataFieldType));
		        		$("#tabComponent").tabs('select', 1);
		        	}  
		        	designField.$data = curTd.field ;
		        }
		    });
		}
	});
});

//初始化
$(function(){
	// 表单
	window.boneform = new BoneForm();
	
	// 添加字段
	// 包括基础组件和常用组件
	var designUI = new Vue({
		el: '#designUI',
		data: {
			basic: FormDesign.get('basic'),
			common: FormDesign.get('common')
		}
	});
	
	// 表单设置
	var designForm = new Vue({
		el: '#designForm',
		created: function() {
			$.syncBoneVue(this, boneform);
		},
		methods: {
			updateTable: function(e){
				$.updateTable(e, this);
			}
		}
	});
	
	// 表格设置
	window.designTable = new Vue({
		el: '#designTable',
		created: function() {
			$.syncBoneVue(this, boneform);
		},
		methods: {
			updateClass: function($this){
				designField.$data = $this.td.field;
	        	$("#designTable td").removeClass('editing');
	        	$($this.$el).toggleClass('editing');
	        	if ( $($this.$el).find('div.fb-field-wrapper').size() > 0 ) {	        		
	        		$("#tabComponent").tabs('select', 1);
	        	}else {
	        		$("#tabComponent").tabs('select', 0);
	        	}
			},
			// 保存表单
			saveform: function() {
				if(confirm("确定保存表单吗？")) {
			   		var data = this.$data;
			   		var bean = new Object();
			   		bean['tableName'] = data.name;
	    			bean['description'] = data.comments;
	    			bean['htmlContent'] = $.getFormbuilderHtml();
	    			bean['json'] = JSON.stringify(data);
	    			bean['type'] = '2';
	    			var field;
	    			var length = data.table.trs[0].tds.length;
	    			var n = 0;
	    			$.each(data.table.trs, function(i, tr) {
	    				$.each(tr.tds, function(j, td){
	    					field = td.field;
	    					if(field.uitype != 'noset'){
	    						bean['customDictionarys[' + n + '].dictionaryName'] = field.name;
	    						bean['customDictionarys[' + n + '].dictionaryDescription'] = field.comments
	    						bean['customDictionarys[' + n + '].dictionaryType'] = field.type;
	    						bean['customDictionarys[' + n + '].remark'] = JSON.stringify(field);
	    						bean['customDictionarys[' + n + '].isList'] = field.inlist?'1':'0';
	    						bean['customDictionarys[' + n + '].isQuery'] = field.search?'1':'0';
	    						bean['customDictionarys[' + n + '].isVerification'] = field.required?'1':'0';
	    						bean['customDictionarys[' + n + '].dictionaryLength'] = $.rightLenth(field);
	    						n++;
	    					}
	    				});
	    			});
			   		//console.log(bean);
			  	  	$.post("/intelligenceReports/customTable/formbuilder.do", bean, function(data) {
			  			if (data.status == "true") {
			  				alert("保存成功");
			  				$(".save-form").attr('disabled', true).text("保存成功");
			  				location.href = '/intelligenceReports/customTable/list.do';
			  			} else {
			  			  	alert(data.error);
			  		  	}
			  	  }, "json");
				} 
			},
			fullscreen: function(e) {
				if( window.top != window.self ){
					var vae = $(e.target).attr('value');
					if (parent.$.fullscreen(vae == '全屏')) {
						$(e.target).attr('value', '缩小');
					}else{
						$(e.target).attr('value', '全屏');
					}
				}
			}
		}
	});
});
