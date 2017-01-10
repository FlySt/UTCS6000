/**
 * jQuery Validation Plugin 1.8.1
 *
 * http://bassistance.de/jquery-plugins/jquery-plugin-validation/
 * http://docs.jquery.com/Plugins/Validation
 *
 * Copyright (c) 2006 - 2011 Jörn Zaefferer
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */
(function(c){c.extend(c.fn,{validate:function(a){if(this.length){var b=c.data(this[0],"validator");if(b)return b;b=new c.validator(a,this[0]);c.data(this[0],"validator",b);if(b.settings.onsubmit){this.find("input, button").filter(".cancel").click(function(){b.cancelSubmit=true});b.settings.submitHandler&&this.find("input, button").filter(":submit").click(function(){b.submitButton=this});this.submit(function(d){function e(){if(b.settings.submitHandler){if(b.submitButton)var f=c("<input type='hidden'/>").attr("name",
b.submitButton.name).val(b.submitButton.value).appendTo(b.currentForm);b.settings.submitHandler.call(b,b.currentForm);b.submitButton&&f.remove();return false}return true}b.settings.debug&&d.preventDefault();if(b.cancelSubmit){b.cancelSubmit=false;return e()}if(b.form()){if(b.pendingRequest){b.formSubmitted=true;return false}return e()}else{b.focusInvalid();return false}})}return b}else a&&a.debug&&window.console&&console.warn("nothing selected, can't validate, returning nothing")},valid:function(){if(c(this[0]).is("form"))return this.validate().form();
else{var a=true,b=c(this[0].form).validate();this.each(function(){a&=b.element(this)});return a}},removeAttrs:function(a){var b={},d=this;c.each(a.split(/\s/),function(e,f){b[f]=d.attr(f);d.removeAttr(f)});return b},rules:function(a,b){var d=this[0];if(a){var e=c.data(d.form,"validator").settings,f=e.rules,g=c.validator.staticRules(d);switch(a){case "add":c.extend(g,c.validator.normalizeRule(b));f[d.name]=g;if(b.messages)e.messages[d.name]=c.extend(e.messages[d.name],b.messages);break;case "remove":if(!b){delete f[d.name];
return g}var h={};c.each(b.split(/\s/),function(j,i){h[i]=g[i];delete g[i]});return h}}d=c.validator.normalizeRules(c.extend({},c.validator.metadataRules(d),c.validator.classRules(d),c.validator.attributeRules(d),c.validator.staticRules(d)),d);if(d.required){e=d.required;delete d.required;d=c.extend({required:e},d)}return d}});c.extend(c.expr[":"],{blank:function(a){return!c.trim(""+a.value)},filled:function(a){return!!c.trim(""+a.value)},unchecked:function(a){return!a.checked}});c.validator=function(a,
b){this.settings=c.extend(true,{},c.validator.defaults,a);this.currentForm=b;this.init()};c.validator.format=function(a,b){if(arguments.length==1)return function(){var d=c.makeArray(arguments);d.unshift(a);return c.validator.format.apply(this,d)};if(arguments.length>2&&b.constructor!=Array)b=c.makeArray(arguments).slice(1);if(b.constructor!=Array)b=[b];c.each(b,function(d,e){a=a.replace(RegExp("\\{"+d+"\\}","g"),e)});return a};c.extend(c.validator,{defaults:{messages:{},groups:{},rules:{},errorClass:"error",
validClass:"valid",errorElement:"label",focusInvalid:true,errorContainer:c([]),errorLabelContainer:c([]),onsubmit:true,ignore:[],ignoreHidden:true,ignoreTitle:false,onfocusin:function(a){this.lastActive=a;if(this.settings.focusCleanup&&!this.blockFocusCleanup){this.settings.unhighlight&&this.settings.unhighlight.call(this,a,this.settings.errorClass,this.settings.validClass);this.addWrapper(this.errorsFor(a)).hide()}},onfocusout:function(a){if(!this.checkable(a)&&(a.name in this.submitted||!this.optional(a)))this.element(a)},
onkeyup:function(a){/*if(a.name in this.submitted||a==this.lastElement)*/this.element(a)},onclick:function(a){if(a.name in this.submitted)this.element(a);else a.parentNode.name in this.submitted&&this.element(a.parentNode)},highlight:function(a,b,d){a.type==="radio"?this.findByName(a.name).addClass(b).removeClass(d):c(a).addClass(b).removeClass(d)},unhighlight:function(a,b,d){a.type==="radio"?this.findByName(a.name).removeClass(b).addClass(d):c(a).removeClass(b).addClass(d)}},setDefaults:function(a){c.extend(c.validator.defaults,
a)},messages:{required:"This field is required.",remote:"Please fix this field.",email:"Please enter a valid email address.",url:"Please enter a valid URL.",date:"Please enter a valid date.",dateISO:"Please enter a valid date (ISO).",number:"Please enter a valid number.",digits:"Please enter only digits.",creditcard:"Please enter a valid credit card number.",equalTo:"Please enter the same value again.",accept:"Please enter a value with a valid extension.",maxlength:c.validator.format("Please enter no more than {0} characters."),
minlength:c.validator.format("Please enter at least {0} characters."),rangelength:c.validator.format("Please enter a value between {0} and {1} characters long."),range:c.validator.format("Please enter a value between {0} and {1}."),max:c.validator.format("Please enter a value less than or equal to {0}."),min:c.validator.format("Please enter a value greater than or equal to {0}.")},autoCreateRanges:false,prototype:{init:function(){function a(e){var f=c.data(this[0].form,"validator");e="on"+e.type.replace(/^validate/,
"");f.settings[e]&&f.settings[e].call(f,this[0])}this.labelContainer=c(this.settings.errorLabelContainer);this.errorContext=this.labelContainer.length&&this.labelContainer||c(this.currentForm);this.containers=c(this.settings.errorContainer).add(this.settings.errorLabelContainer);this.submitted={};this.valueCache={};this.pendingRequest=0;this.pending={};this.invalid={};this.reset();var b=this.groups={};c.each(this.settings.groups,function(e,f){c.each(f.split(/\s/),function(g,h){b[h]=e})});var d=this.settings.rules;
c.each(d,function(e,f){d[e]=c.validator.normalizeRule(f)});c(this.currentForm).validateDelegate(":text, :password, :file, select, textarea","focusin focusout keyup",a).validateDelegate(":radio, :checkbox, select, option","click",a);this.settings.invalidHandler&&c(this.currentForm).bind("invalid-form.validate",this.settings.invalidHandler)},form:function(){this.checkForm();c.extend(this.submitted,this.errorMap);this.invalid=c.extend({},this.errorMap);this.valid()||c(this.currentForm).triggerHandler("invalid-form",
[this]);this.showErrors();return this.valid()},checkForm:function(){this.prepareForm();for(var a=0,b=this.currentElements=this.elements();b[a];a++)this.check(b[a]);return this.valid()},element:function(a){this.lastElement=a=this.clean(a);this.prepareElement(a);this.currentElements=c(a);var b=this.check(a);if(b)delete this.invalid[a.name];else this.invalid[a.name]=true;if(!this.numberOfInvalids())this.toHide=this.toHide.add(this.containers);this.showErrors();return b},showErrors:function(a){if(a){c.extend(this.errorMap,
a);this.errorList=[];for(var b in a)this.errorList.push({message:a[b],element:this.findByName(b)[0]});this.successList=c.grep(this.successList,function(d){return!(d.name in a)})}this.settings.showErrors?this.settings.showErrors.call(this,this.errorMap,this.errorList):this.defaultShowErrors()},resetForm:function(){c.fn.resetForm&&c(this.currentForm).resetForm();this.submitted={};this.prepareForm();this.hideErrors();this.elements().removeClass(this.settings.errorClass);this.elements().removeClass("validform-state-highlight");$(".validform-checktip").each(function(){ $(this).parent(".validator-error-info").remove();});},numberOfInvalids:function(){return this.objectLength(this.invalid)},
objectLength:function(a){var b=0,d;for(d in a)b++;return b},hideErrors:function(){this.addWrapper(this.toHide).each(function(i,e){$(this).parents(".validator-error-info").hide()});},valid:function(){return this.size()==0},size:function(){return this.errorList.length},focusInvalid:function(){if(this.settings.focusInvalid)try{c(this.findLastActive()||this.errorList.length&&this.errorList[0].element||[]).filter(":visible").focus().trigger("focusin")}catch(a){}},findLastActive:function(){var a=this.lastActive;return a&&c.grep(this.errorList,function(b){return b.element.name==
a.name}).length==1&&a},elements:function(){var a=this,b={};return c(this.currentForm).find("input, select, textarea").not(":submit,"+(this.settings.ignoreHidden === true?":hidden,":"")+":reset, :image, [disabled]").not(this.settings.ignore).filter(function(){!this.name&&a.settings.debug&&window.console&&console.error("%o has no name assigned",this);if(this.name in b||!a.objectLength(c(this).rules()))return false;return b[this.name]=true})},clean:function(a){return c(a)[0]},errors:function(){return c(this.settings.errorElement+"."+this.settings.errorClass,
this.errorContext)},reset:function(){this.successList=[];this.errorList=[];this.errorMap={};this.toShow=c([]);this.toHide=c([]);this.currentElements=c([])},prepareForm:function(){this.reset();this.toHide=this.errors().add(this.containers)},prepareElement:function(a){this.reset();this.toHide=this.errorsFor(a)},check:function(a){a=this.clean(a);if(this.checkable(a))a=this.findByName(a.name).not(this.settings.ignore)[0];var b=c(a).rules(),d=false,e;for(e in b){var f={method:e,parameters:b[e]};try{var g=
c.validator.methods[e].call(this,a.value.replace(/\r/g,""),a,f.parameters);if(g=="dependency-mismatch")d=true;else{d=false;if(g=="pending"){this.toHide=this.toHide.not(this.errorsFor(a));return}if(!g){this.formatAndAdd(a,f);return false}}}catch(h){this.settings.debug&&window.console&&console.log("exception occured when checking element "+a.id+", check the '"+f.method+"' method",h);throw h;}}if(!d){this.objectLength(b)&&this.successList.push(a);return true}},customMetaMessage:function(a,b){if(c.metadata){var d=
this.settings.meta?c(a).metadata()[this.settings.meta]:c(a).metadata();return d&&d.messages&&d.messages[b]}},customMessage:function(a,b){var d=this.settings.messages[a];return d&&(d.constructor==String?d:d[b])},findDefined:function(){for(var a=0;a<arguments.length;a++)if(arguments[a]!==undefined)return arguments[a]},defaultMessage:function(a,b){return this.findDefined(this.customMessage(a.name,b),this.customMetaMessage(a,b),!this.settings.ignoreTitle&&a.title||undefined,c.validator.messages[b],"<strong>Warning: No message defined for "+
a.name+"</strong>")},formatAndAdd:function(a,b){var d=this.defaultMessage(a,b.method),e=/\$?\{(\d+)\}/g;if(typeof d=="function")d=d.call(this,b.parameters,a);else if(e.test(d))d=jQuery.format(d.replace(e,"{$1}"),b.parameters);this.errorList.push({message:d,element:a});this.errorMap[a.name]=d;this.submitted[a.name]=d},addWrapper:function(a){if(this.settings.wrapper)a=a.add(a.parent(this.settings.wrapper));return a},defaultShowErrors:function(){for(var a=0;this.errorList[a];a++){var b=this.errorList[a];
this.settings.highlight&&this.settings.highlight.call(this,b.element,this.settings.errorClass,this.settings.validClass);this.showLabel(b.element,b.message)}if(this.errorList.length)this.toShow=this.toShow.add(this.containers);if(this.settings.success)for(a=0;this.successList[a];a++)this.showLabel(this.successList[a]);if(this.settings.unhighlight){a=0;for(b=this.validElements();b[a];a++)this.settings.unhighlight.call(this,b[a],this.settings.errorClass,this.settings.validClass)}this.toHide=this.toHide.not(this.toShow);
this.hideErrors();this.addWrapper(this.toShow).show()},validElements:function(){return this.currentElements.not(this.invalidElements())},invalidElements:function(){return c(this.errorList).map(function(){return this.element})},showLabel:function(a,b){var d=this.errorsFor(a);if(d.length){d.removeClass().addClass(this.settings.errorClass);d.parent().parent().show();d.attr("generated")&&d.html(b)}else{d=c("<"+this.settings.errorElement+"/>").attr({"for":this.idOrName(a),generated:true}).addClass(this.settings.errorClass).html(b||
"");if(this.settings.wrapper)d=d.hide().show().wrap("<"+this.settings.wrapper+"/>").parent();this.labelContainer.append(d).length||(this.settings.errorPlacement?this.settings.errorPlacement(d,c(a)):d.insertAfter(a))}if(!b&&this.settings.success){d.text("");typeof this.settings.success=="string"?d.addClass(this.settings.success):this.settings.success(d)}this.toShow=this.toShow.add(d)},errorsFor:function(a){var b=this.idOrName(a);return this.errors().filter(function(){return c(this).attr("for")==b})},
idOrName:function(a){return this.groups[a.name]||(this.checkable(a)?a.name:a.id||a.name)},checkable:function(a){return/radio|checkbox/i.test(a.type)},findByName:function(a){var b=this.currentForm;return c(document.getElementsByName(a)).map(function(d,e){return e.form==b&&e.name==a&&e||null})},getLength:function(a,b){switch(b.nodeName.toLowerCase()){case "select":return c("option:selected",b).length;case "input":if(this.checkable(b))return this.findByName(b.name).filter(":checked").length}return a.length},
depend:function(a,b){return this.dependTypes[typeof a]?this.dependTypes[typeof a](a,b):true},dependTypes:{"boolean":function(a){return a},string:function(a,b){return!!c(a,b.form).length},"function":function(a,b){return a(b)}},optional:function(a){return!c.validator.methods.required.call(this,c.trim(a.value),a)&&"dependency-mismatch"},startRequest:function(a){if(!this.pending[a.name]){this.pendingRequest++;this.pending[a.name]=true}},stopRequest:function(a,b){this.pendingRequest--;if(this.pendingRequest<
0)this.pendingRequest=0;delete this.pending[a.name];if(b&&this.pendingRequest==0&&this.formSubmitted&&this.form()){c(this.currentForm).submit();this.formSubmitted=false}else if(!b&&this.pendingRequest==0&&this.formSubmitted){c(this.currentForm).triggerHandler("invalid-form",[this]);this.formSubmitted=false}},previousValue:function(a){return c.data(a,"previousValue")||c.data(a,"previousValue",{old:null,valid:true,message:this.defaultMessage(a,"remote")})}},classRuleSettings:{required:{required:true},
email:{email:true},url:{url:true},date:{date:true},dateISO:{dateISO:true},dateDE:{dateDE:true},number:{number:true},numberDE:{numberDE:true},digits:{digits:true},creditcard:{creditcard:true}},addClassRules:function(a,b){a.constructor==String?this.classRuleSettings[a]=b:c.extend(this.classRuleSettings,a)},classRules:function(a){var b={};(a=c(a).attr("class"))&&c.each(a.split(" "),function(){this in c.validator.classRuleSettings&&c.extend(b,c.validator.classRuleSettings[this])});return b},attributeRules:function(a){var b=
{};a=c(a);for(var d in c.validator.methods){var e=a.attr(d);if(e)b[d]=e}b.maxlength&&/-1|2147483647|524288/.test(b.maxlength)&&delete b.maxlength;return b},metadataRules:function(a){if(!c.metadata)return{};var b=c.data(a.form,"validator").settings.meta;return b?c(a).metadata()[b]:c(a).metadata()},staticRules:function(a){var b={},d=c.data(a.form,"validator");if(d.settings.rules)b=c.validator.normalizeRule(d.settings.rules[a.name])||{};return b},normalizeRules:function(a,b){c.each(a,function(d,e){if(e===
false)delete a[d];else if(e.param||e.depends){var f=true;switch(typeof e.depends){case "string":f=!!c(e.depends,b.form).length;break;case "function":f=e.depends.call(b,b)}if(f)a[d]=e.param!==undefined?e.param:true;else delete a[d]}});c.each(a,function(d,e){a[d]=c.isFunction(e)?e(b):e});c.each(["minlength","maxlength","min","max"],function(){if(a[this])a[this]=Number(a[this])});c.each(["rangelength","range"],function(){if(a[this])a[this]=[Number(a[this][0]),Number(a[this][1])]});if(c.validator.autoCreateRanges){if(a.min&&
a.max){a.range=[a.min,a.max];delete a.min;delete a.max}if(a.minlength&&a.maxlength){a.rangelength=[a.minlength,a.maxlength];delete a.minlength;delete a.maxlength}}a.messages&&delete a.messages;return a},normalizeRule:function(a){if(typeof a=="string"){var b={};c.each(a.split(/\s/),function(){b[this]=true});a=b}return a},addMethod:function(a,b,d){c.validator.methods[a]=b;c.validator.messages[a]=d!=undefined?d:c.validator.messages[a];b.length<3&&c.validator.addClassRules(a,c.validator.normalizeRule(a))},
methods:{required:function(a,b,d){if(!this.depend(d,b))return"dependency-mismatch";switch(b.nodeName.toLowerCase()){case "select":return(a=c(b).val())&&a.length>0;case "input":if(this.checkable(b))return this.getLength(a,b)>0;default:return c.trim(a).length>0}},remote:function(a,b,d){if(this.optional(b))return"dependency-mismatch";var e=this.previousValue(b);this.settings.messages[b.name]||(this.settings.messages[b.name]={});e.originalMessage=this.settings.messages[b.name].remote;this.settings.messages[b.name].remote=
e.message;d=typeof d=="string"&&{url:d}||d;if(this.pending[b.name])return"pending";if (e.old !== a || d.ignoreSameValue ) {e.old = a;}else{return e.valid;}var f=this;this.startRequest(b);var g={};g[b.name]=a;c.ajax(c.extend(true,{url:d,mode:"abort",port:"validate"+b.name,dataType:"json",data:g,success:function(h){f.settings.messages[b.name].remote=e.originalMessage;var j=h===true;if(j){var i=f.formSubmitted;f.prepareElement(b);f.formSubmitted=i;f.successList.push(b);f.showErrors()}else{i={};h=h||f.defaultMessage(b,"remote");i[b.name]=
e.message=c.isFunction(h)?h(a):h;f.showErrors(i)}e.valid=j;f.stopRequest(b,j)}},d));return"pending"},minlength:function(a,b,d){return this.optional(b)||this.getLength(c.trim(a),b)>=d},maxlength:function(a,b,d){return this.optional(b)||this.getLength(c.trim(a),b)<=d},rangelength:function(a,b,d){a=this.getLength(c.trim(a),b);return this.optional(b)||a>=d[0]&&a<=d[1]},min:function(a,b,d){return this.optional(b)||a>=d},max:function(a,b,d){return this.optional(b)||Number(a)<=d},range:function(a,b,d){return this.optional(b)||
a>=d[0]&&a<=d[1]},email:function(a,b){return this.optional(b)||/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(a)},
url:function(a,b){return this.optional(b)||/^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(a)},
date:function(a,b){return this.optional(b)||!/Invalid|NaN/.test(new Date(a))},dateISO:function(a,b){return this.optional(b)||/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test(a)},number:function(a,b){return this.optional(b)||/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(a)},digits:function(a,b){return this.optional(b)||/^\d+$/.test(a)},creditcard:function(a,b){if(this.optional(b))return"dependency-mismatch";if(/[^0-9-]+/.test(a))return false;var d=0,e=0,f=false;a=a.replace(/\D/g,"");for(var g=a.length-1;g>=
0;g--){e=a.charAt(g);e=parseInt(e,10);if(f)if((e*=2)>9)e-=9;d+=e;f=!f}return d%10==0},accept:function(a,b,d){d=typeof d=="string"?d.replace(/,/g,"|"):"png|jpe?g|gif";return this.optional(b)||a.match(RegExp(".("+d+")$","i"))},equalTo:function(a,b,d){d=c(d).unbind(".validate-equalTo").bind("blur.validate-equalTo",function(){c(b).valid()});return a==d.val()}}});c.format=c.validator.format})(jQuery);
(function(c){var a={};if(c.ajaxPrefilter)c.ajaxPrefilter(function(d,e,f){e=d.port;if(d.mode=="abort"){a[e]&&a[e].abort();a[e]=f}});else{var b=c.ajax;c.ajax=function(d){var e=("port"in d?d:c.ajaxSettings).port;if(("mode"in d?d:c.ajaxSettings).mode=="abort"){a[e]&&a[e].abort();return a[e]=b.apply(this,arguments)}return b.apply(this,arguments)}}})(jQuery);
(function(c){!jQuery.event.special.focusin&&!jQuery.event.special.focusout&&document.addEventListener&&c.each({focus:"focusin",blur:"focusout"},function(a,b){function d(e){e=c.event.fix(e);e.type=b;return c.event.handle.call(this,e)}c.event.special[b]={setup:function(){this.addEventListener(a,d,true)},teardown:function(){this.removeEventListener(a,d,true)},handler:function(e){arguments[0]=c.event.fix(e);arguments[0].type=b;return c.event.handle.apply(this,arguments)}}});c.extend(c.fn,{validateDelegate:function(a,
b,d){return this.bind(b,function(e){var f=c(e.target);if(f.is(a))return d.apply(f,arguments)})}})})(jQuery);
/**
 * 扩张提示错误样式
 * @param {Object} input
 */
$.validator.setDefaults({
	//错误样式
	errorClass:"error-tip",
	// 错误事实添加的CSS 类
	highlight: function(input) {
	    $(input).addClass("validform-state-highlight");
	},
	// 完成时候移出的类
	unhighlight: function(input) {
		$(input).removeClass("validform-state-highlight");
	},
   onfocusin: function(element) {
        this.lastActive = element;
        
        // input类型设置stringMaxLength或maxlength后，设置该input的maxlength属性
        var elementRule = this.settings.rules[element.id];
        if(elementRule && element.type == 'text'){
            var stringMaxLength = 0;
            var _maxLength = elementRule["maxlength"] ? elementRule["maxlength"] : 0;
            var _stringMaxLength = elementRule["stringMaxLength"] ? elementRule["stringMaxLength"] : 0;
            stringMaxLength = _maxLength > _stringMaxLength ? _maxLength : _stringMaxLength;
            if(stringMaxLength > 0){
                element.maxLength = stringMaxLength;
            }
        }
        
        if ( this.settings.focusCleanup && !this.blockFocusCleanup ) {
            this.settings.unhighlight && this.settings.unhighlight.call( this, element, this.settings.errorClass, this.settings.validClass );
            this.addWrapper(this.errorsFor(element)).hide();
        }
        //添加了校验框获取焦点后进行元素校验
        var val = $(element).val();
        if(val != ""){
            if ( !this.checkable(element) && (element.name in this.submitted || !this.optional(element)) ) {
                this.element(element);
            }
        }
    },
	success:function(element){element.parent().parent().empty().remove()},
	
	errorPlacement:function(error,element){
		var style = "";
		var n = element.attr("errorLeft");
		if(n)style += "left:" + n;
		n = element.attr("errorTop");
		if(n)style += "px;top:" + n;
		
		var html='<div class="validator-error-info" onclick="$(this).hide();" style="' + style + 'px;">' +
				'<span class="validform-checktip"></span>' +
				'<span class="validator-dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span>' +
				'</div>';
		var div = $(html);
   		error.appendTo(div.find(".validform-checktip"));//将error元素放入'validform-checktip'中
   		element.after(div);		//将div放入到校验元素的后面
   		
   		//调整验证的框的距离
   		//var content = div.parent().find(".validator-error-info").last();
   		//var content = div;
   		//var pos1 = $(element[0].offsetParent).offset();
   		//var pos = {left:element[0].offsetLeft + pos1.left,top:element[0].offsetTop + pos1.top};
   		//div.offset({top:pos.top +25,left:pos.left - 10});
   		var offsetElement = element.offset();
   		var bodyWidth = document.body.clientWidth - 10;
   		var pos = {top:offsetElement.top + 25,left:offsetElement.left -10};
   		//div.offset({top:pos.top,left:pos.left});
//   		if(Number(element.attr("errorLeft")) != pos.left || Number(element.attr("errorTop")) != pos.top){
//   			div.offset({top:pos.top,left:pos.left});
//   	   		element.attr("errorLeft", pos.left);
//   	   		element.attr("errorTop", pos.top);
//   		}
   		//此处缓存校验信息的位置信息，主要解决在ie8下如果有开360的情况下在浏览器失去焦点的情况下会崩溃的问题
   		var offsetParent = $(div[0].offsetParent).offset();
   		
   		if(Number(element.attr("errorTop")) != (pos.top - offsetParent.top)){
   			div[0].style.top = (pos.top - offsetParent.top) + "px";
   			element.attr("errorTop", pos.top - offsetParent.top);
   		}
   		if(Number(element.attr("errorLeft")) != (pos.left - offsetParent.left)){
   			div[0].style.left = (pos.left - offsetParent.left) + "px";
   			element.attr("errorLeft", pos.left - offsetParent.left);
   		}
   		
   		var errObj = div.find(".error-tip");
   		var errWidth = errObj.width();
   		if(offsetElement.left + errWidth + 20 >= bodyWidth){
   			errObj.css({
   				"white-space":"normal",
   				"width":(bodyWidth - offsetElement.left - 20) + "px"
   				});
   			div.css("width",(bodyWidth - offsetElement.left) + "px");
   		}

    }
});
$.validator.placeFollow = function(opt){
	if(opt == null) return;
	$(opt).find(".validator-error-info").each(function(i,ele){
		element = $(this).prev("input"); 		
		var offsetElement = element.offset();
		if(!offsetElement){
			element = $(this).prev("select");
			offsetElement = element.offset();
			
			if(!offsetElement){
				element = $(this).prev("textarea");
				offsetElement = element.offset();
			}
		}
		
   		var pos = {top:offsetElement.top + 25,left:offsetElement.left -10};
   		$(this).offset({top:pos.top,left:pos.left});
	});
}
/****validate.messages_cn.js****/
jQuery.extend(jQuery.validator.messages, {
	required: "必填字段",
	remote: "请修正该字段",
	email: "请输入正确格式的电子邮件",
	url: "请输入合法的网址",
	date: "请输入合法的日期",
	dateISO: "请输入合法的日期 (ISO).",
	number: "请输入合法的数字",
	digits: "只能输入整数",
	creditcard: "请输入合法的信用卡号",
	equalTo: "请再次输入相同的值",
	accept: "请输入拥有合法后缀名的字符串",
	maxlength: jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),
	minlength: jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),
	justlength: jQuery.validator.format("请输入一个长度为 {0} 的字符串"),
	rangelength: jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
	range: jQuery.validator.format("整数范围({0}-{1})"),//字数太长会有超出form的危险
	max: jQuery.validator.format("整数最大值为 {0}"),
	min: jQuery.validator.format("整数最小值为 {0}")
	});
/****jquery.validate.userdef.js****/
var MAX_EMAIL_LENGTH = 30;
var MAX_USER_NAME = 30;
var MAX_INDEX_CODE = 32;

$(document).ready(function(){
	
	//ip地址验证
	jQuery.validator.addMethod("isIp",function(value,element){
		var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
	    return this.optional(element) || (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
	}, "Ip地址格式错误");
	
	// 手机号码验证   
	jQuery.validator.addMethod("isMobile", function(value, element) {   
	  var length = value.length;   
	  var mobile = /^(1[3|4|5|8][0-9]\d{8})$/;   
	  return this.optional(element) || (length == 11 && mobile.test(value));   
	}, "请正确填写您的手机号码");   
	    
	// 电话号码验证       
	jQuery.validator.addMethod("isTel", function(value, element) {       
		var tel = /^0\d{3}-?\d{7,8}$/;
		var tel2 = /^0\d{2}-?\d{8,9}$/;
		return this.optional(element) || (tel.test(value) || tel2.test(value));       
	}, "请正确填写您的电话号码");   

	// 联系电话(手机/电话皆可)验证   
	jQuery.validator.addMethod("isPhone", function(value,element) {   
	var length = value.length;   
	var mobile = /^(1[3|4|5|8][0-9]\d{8})$/;   
	var tel = /^0\d{3}-?\d{7,8}$/;
	var tel2 = /^0\d{2}-?\d{8,9}$/;
	return this.optional(element) || (tel.test(value) || tel2.test(value) || mobile.test(value));   
	}, "请正确填写您的联系电话");

	// 邮政编码验证       
	jQuery.validator.addMethod("isZipCode", function(value, element) {       
	var tel = /^[0-9]{6}$/;       
	return this.optional(element) || (tel.test(value));       
	}, "请正确填写您的邮政编码"); 

	// 身份证号码验证       
	jQuery.validator.addMethod("isIdCardNo", function(value, element) { 
	return this.optional(element) || /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/.test(value)||/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/.test(value);       
	}, "请正确输入您的身份证号码");

	// 车牌号码后半段 验证 :例如 A090BJ       
	jQuery.validator.addMethod("isCarNum", function(value,element) { 
		var patrn=/^[a-zA-Z_0-9]{4,9}[挂警学]{0,1}$/; //车牌号
		if (patrn.exec(value))
			return true ;
		else 
			return false;
	}, "请正确输入车牌号码");
	
	// 车牌号码后半段 验证 :例如 A090BJ       
	jQuery.validator.addMethod("isAllCarNum", function(value,element) { 
		value = value.substring(1);
		var patrn=/^[a-zA-Z_0-9]{4,9}[挂警学]{0,1}$/; //车牌号
		if (patrn.exec(value))
			return true ;
		else 
			return false;
	}, "请正确输入车牌号码");
	
	//最大长度验证
	jQuery.validator.addMethod("stringMaxLength", function(value, element, param) {
		value = $.trim(value);
        // 超过定义长度的截断
		var length = value.length;
        if(length > param){
           // element.value = value.slice(0, param);
        	return false;
        }
        
//		for ( var i = 0; i < length; i++) {
//			if (value.charCodeAt(i) > 127) {
//				length++;
//			}
//		}
        
		return true;//this.optional(element) || (length <= param);
		}, $.validator.format("长度不能大于{0}"));

	// 输入字符长度范围验证，中文字两个字节
	jQuery.validator.addMethod("stringRangeLength", function(value, element, param) {
		var length = value.length;
		for(var i = 0; i < value.length; i++){
			if(value.charCodeAt(i) > 127){
				length++;
			}
		}
		return this.optional(element) || ( length >= param[0] && length <= param[1] );
	}, $.validator.format("长度介于 {0}-{1} 之间(一个中文算2位)"));

	/*		//不允许特殊字符
	jQuery.validator.addMethod("normalStr", function(value, element) {
		return this.optional(element) || /^[u0391-uFFE5w]+$/.client(value);
		}, "不允许包含特殊符号!");


	//只能包括英文字母和数字
	jQuery.validator.addMethod("alnum", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9]+$/.client(value);
		}, "只能包括英文字母和数字");

	 */
	//只能包括英文字母和数字
	jQuery.validator.addMethod("isNum", function(value, element) {
//		if(value.indexOf(" ")>-1){
//			element.value = $.trim(element.value);
//		}
		var patrn=/^([ ]*)([0-9]*)([ ]*)+$/; 
		return this.optional(element) || patrn.test(value);
		}, "只能包括数字");
	
	//校验正则表达式
	jQuery.validator.addMethod("regexp",function(value,element,regexp){
	    return this.optional(element) || (regexp.test(value));
	},"输入不符合规定");
	
	//指定特殊字符校验
	jQuery.validator.addMethod("filterWord", function(value, element,filter) {
			//定义特殊的字符数组,如果指定了过滤的特殊字符就使用指定的，没有则使用默认的特殊字符
		var specialWordArray = ["'",":","*","\"","?","＊","？","<",">","|", "/", "%","\\",'"',"#","%","@","$","^","&","·","•"];
		if(filter!=true){
			var param = filter;
			if(!!param){
				if(typeof param === "string"){
					specialWordArray = [];
					for(var i = 0; i < param.length; i ++){
						specialWordArray.push(param.slice(i,i+1));
					}
				}
				if(typeof param === "object"){
					specialWordArray = param;
				}
			}
		}
		for(var i = 0; i < specialWordArray.length; i ++){
			if(value.indexOf(specialWordArray[i]) == -1){
				continue;
			}else{
				return false;	//包含特殊字符
			};
		}
		return true;
	},"不能包含特殊字符");
	
	//指定特殊字符校验
	jQuery.validator.addMethod("filterWords", function(value, element,filter) {
			//定义特殊的字符数组,如果指定了过滤的特殊字符就使用指定的，没有则使用默认的特殊字符
		//var specialWordArray = ["'",":","*","\"","?","＊","？","<",">","|", "/", "%","\\",'"'];
		var specialWordArray = ["'","*","\"","＊",'？',"<",">","|", "%",'"',"#","%","_","@","$","^","&","·","•"];
		if(filter!=true){
			var param = filter;
			if(!!param){
				if(typeof param === "string"){
					specialWordArray = [];
					for(var i = 0; i < param.length; i ++){
						specialWordArray.push(param.slice(i,i+1));
					}
				}
				if(typeof param === "object"){
					specialWordArray = param;
				}
			}
		}
		for(var i = 0; i < specialWordArray.length; i ++){
			if(value.indexOf(specialWordArray[i]) == -1){
				continue;
			}else{
				return false;	//包含特殊字符
			};
		}
		return true;
	},"不能包含特殊字符");
	
	jQuery.validator.addMethod("stringMinLength", function(value, element, param) {
        // 超过定义长度的截断
		var length = value.length;
        if(length==0||length >= param){
        	return true;
        }
	}, $.validator.format("长度小于{0}"));
		
	jQuery.validator.addMethod("affirmPwd", function(value, element,userPassword) {
		var reg = $(userPassword).val();
		if(value == reg){
			return true;
		}
		//element.focus();
	}, "两次输入密码不一致");
	
	jQuery.validator.addMethod("affirmStartDate", function(value, element,endTime) {
		var reg = $(endTime).val();
		if(value < reg){
			return true;
		}
	}, "开始时间必须小于结束时间");
	
	jQuery.validator.addMethod("affirmDate", function(value, element,startTime) {
		var reg = $(startTime).val();
		if(value > reg){
			return true;
		}
	}, "结束时间需大于开始时间");
	
	//判断下拉选项
	jQuery.validator.addMethod("selectOption", function(value, element) {
		if(value==0){
			return false;
		}
		return true;
	}, "请选择部门");

	//判断下拉选项
	jQuery.validator.addMethod("selectMonitorOption", function(value, element) {
		if(value==0){
			return false;
		}
		return true;
	}, "请选择监控点");
	
	//判断下拉选项
	jQuery.validator.addMethod("selectControlReasonOption", function(value, element) {
		if(value==0){
			return false;
		}
		return true;
	}, "请选择布控原因");
	
	//判断下拉选项
	jQuery.validator.addMethod("selectPlateTypeOption", function(value, element) {
		if(value==0){
			return false;
		}
		return true;
	}, "请选择号码种类");
	
	//判断下拉选项
	jQuery.validator.addMethod("selectDirOption", function(value, element) {
		if(value==0){
			return false;
		}
		return true;
	}, "请选择行驶方向");
	
	//添加用户帐号判断
	jQuery.validator.addMethod("userAccount", function(value, element) {
		if(value== 'NcjkRj' || value== 'NcjkGc' || value== 'Admin'){
			return false;
		}
		return true;
	}, "此帐号为关键字");
	
		// 只能输入字母及数字         
	jQuery.validator.addMethod("isLetterNum", function(value,element) { 
		//var patrn=/^[A-Za-z0-9]+$/;
		var patrn=/^([ ]*)([A-Za-z0-9]*)([ ]*)+$/; 
		if (value == "" || patrn.test(value))
			return true ;
		else 
			return false;
	}, "请正确输入字母或数字");
	
	// 只能输入字母及数字 和“.”       
	jQuery.validator.addMethod("isLetterNumPeriod", function(value,element) { 
		//var patrn=/^[A-Za-z0-9]+$/;
		var patrn=/^([ ]*)([A-Za-z0-9.]*)([ ]*)+$/; 
		if (value == "" || patrn.test(value)){
			return true ;
		}else {
			return false;
		}
	}, "请正确输入字母或数字或IP");
	
	// 只能输入字母及数据    或者 为空时       
	jQuery.validator.addMethod("isDouble", function(value,element) { 
		//var patrn=/^[-+]?[0-9]*\.?[0-9]+$/;
		var patrn=/^([ ]*)(([-+]?([0-9]+\.[0-9]+|[0-9]+))*)([ ]*)$/; 
		//var patrn=/^[-+]?([0-9]+\.[0-9]+|[0-9]+)$/;
		if (value == "" || patrn.test(value)){
			return true ;
		}
	}, "请正确输入整数或小数");
	
	//jpg,jpeg,png和gif
	//图片格式验证  || postfix == ".jpeg" 
	jQuery.validator.addMethod("checkIsImage", function(value, element,extName) {
		var reg = $(extName).val();
		reg = reg.toLowerCase();  //转小写
		var postfix = "";
		postfix = reg.substring(reg.lastIndexOf("."));
		if(value == "" || postfix == ".jpg" || postfix == ".png"  || postfix == ".gif" || postfix == ".bmp"){
			return true;
		}
		//$("#fileupload")[0].focus();
	}, "上传的文件类型错误，请选择jpg,png,gif和bmp格式的图片!");
	
	
	//判断长度定长
	jQuery.validator.addMethod("justlength", function(value, element, param) {
        // 超过定义长度的截断
		var length = value.length;
		if(length==0){
			return true;
		}
        if(length== param){
        	return true;
        }
        if(length>param){
        //	element.value = value.slice(0, param);
        	return false;
        }
	}, $.validator.format("输入长度必须为{0}"));
	
	//输入数据不为某个值
	jQuery.validator.addMethod("isTheString", function(value, element, param) {
        // 超过定义长度的截断
		if(value==param[2]){
			return false;
		}
		return true;//this.optional(element) || (length <= param);
	}, $.validator.format("{0}不能为{1}"));
	
	jQuery.validator.addMethod("storeHost", function(value, element) {
		//$("#storeHost2").attr('checked')=='checked'
		if(($("#storeHost1").attr('checked')=='checked' && value==0) || ($("#storeHost2").attr('checked')=='checked' && value!=0)){
			return true;
		}
	}, "请选择图片管理服务器");
	
	//判断下拉选项
	jQuery.validator.addMethod("storeSelectOption", function(value, element) {
		if(value==0){
			return false;
		}
		return true;
	}, "请选择存储磁盘");
	
	//判断下拉选项
	jQuery.validator.addMethod("accessServerSelectOption", function(value, element) {
		if(value==0){
			return false;
		}
		return true;
	}, "请选择接入服务器");
});

jQuery.validator.addMethod("notBeforeDate", function(value, element,startTime) {
	var reg = $(startTime).val();
	if(value >= reg){
		return true;
	}
}, "结束时间需不小于开始时间");
