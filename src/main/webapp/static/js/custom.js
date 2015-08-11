/**
 * 注销
 */
function logout() {
	$.ajax({
				url : 'user/logout.htm',
				type : 'post',
				cache : false,
				complete : function() {
					window.location.replace("index.htm");
				}
			});
};

/**
 * 修改密码
 */
function resetPwd() {
	var saveBtn = $("button#reset-pwd-window-save-btn");
	saveBtn.attr('disabled', "disabled");

	fldOriginalPwd = $("input#reset-pwd-window-original-pwd");
	fldNewPwd = $("input#reset-pwd-window-new-pwd");
	fldConfirmPwd = $("input#reset-pwd-window-confirm-pwd");

	var validateSpan = $("span#reset-pwd-window-error-text");

	var originalPwd = fldOriginalPwd.val();
	var newPwd = fldNewPwd.val();
	var confirmPwd = fldConfirmPwd.val();

	originalPwd = $.trim(originalPwd);
	newPwd = $.trim(newPwd);
	confirmPwd = $.trim(confirmPwd);

	if (originalPwd == '' || newPwd == '' || confirmPwd == '') {
		validateSpan.html("密码不允许为空");
		saveBtn.removeAttr('disabled');
		return;
	} else if (newPwd != confirmPwd) {
		validateSpan.html("新密码两次输入不相同");
		saveBtn.removeAttr('disabled');
		return;
	}

	$.ajax({
				url : 'user/resetpwd.htm',
				type : 'post',
				dataType : 'json',
				data : {
					opwd : originalPwd,
					pwd : newPwd
				},
				cache : false,
				success : function(data) {
					if (data.errors.length == 0) {
						saveBtn.removeAttr('disabled');
						fldOriginalPwd.val('');
						fldNewPwd.val('');
						fldConfirmPwd.val('');
						$('#reset-pwd-window').modal('hide')
					} else {
						saveBtn.removeAttr('disabled');
						validateSpan.html(data.errors[0].msg);
					}
				},
				error : function() {
					validateSpan.html("修改密码失败");
					saveBtn.removeAttr('disabled');
				}
			});
};

/**
 * 模态表单窗口的错误信息提示函数，该模态窗口的modal-body 中必须包含一个ID属性值为error-text的span标签
 * 
 * @param {}
 *            modalWin 模态表单窗口实例
 * @param {}
 *            message 表单错误提示信息
 */
function waringFormError(modalWin, message) {
	modalWin.find('.modal-body span#error-text').html(message);
}

/**
 * 系统提示窗口封装函数
 * 
 * @param alertContent
 *            提示内容
 */
function systemAlert(alertContent) {
	$('div#system-alert-window .modal-body span').html(alertContent);
	$('div#system-alert-window').modal('show');
};

/**
 * 获取当前日期
 * 
 * @returns {String}
 */
function getCurrentDateStr() {
	var mydate = new Date();
	var str = "" + mydate.getFullYear() + "-";
	str += (mydate.getMonth() + 1) + "-";
	str += mydate.getDate();
	return str;
};

/**
 * 查询出入库统计
 */
function queryReports(reportType) {
	var duration = $('input#query-time').val(); // 查询时间区间
	var keyword = $('input#keyword').val(); // 查询关键字
	var url = "";
	if (reportType === 0) {
		url = ctx + '/warehouse/checkInReport.htm';
	} else if (reportType === 1) {
		url = ctx + '/warehouse/checkOutReport.htm';
	} else if (reportType === 2) {
		url = ctx + '/warehouse/aggregateReport.htm';
	}
	// 刷新表格
	$('table#warehouse-report-table').bootstrapTable('refresh', {
				url : url,
				query : {
					'duration' : duration,
					'keyword' : keyword
				}
			});

	// 刷新总量
	$.ajax({
				url : 'warehouse/queryTotalCount.htm',
				type : 'post',
				dataType : 'json',
				data : {
					'type' : reportType,
					'duration' : duration,
					'keyword' : keyword
				},
				cache : false,
				success : function(data) {
					if (data.errors.length == 0) {
						if (data.data) {
							if (data.data.hasOwnProperty("totalInCount")) {
								$('#totalInCount').html('入库总量:'
										+ data.data.totalInCount);
							}
							if (data.data.hasOwnProperty("totalOutCount")) {
								$('#totalOutCount').html('出库总量:'
										+ data.data.totalOutCount);
							}
						}
					} else {
						systemAlert('查询总计总量失败!');
					}
				},
				error : function() {
					systemAlert('查询总计总量失败!');
				}
			});
};

function userTableStateFormatter(value, row, index) {

	if (row.status === "1") {
		return value;

	} else {
		return {
			disabled : true
		};
	}
};

function userStatusFormatter(value, row) {
	if (value === "0") {
		return '<span style="color: red;">已删除</span>';
	} else if (value === "1") {
		return '可使用';
	}
};

function user_add() {

	$("input[name='rights']").each(function() {
				$(this).attr("checked", false);
			});
	var modalWin = $('div#user-reg-window');
	$('#user-manage-title').html("添加用户");
	$('#reg-id').val(-1);
	modalWin.modal('show');
	var fldCompany = modalWin.find('.modal-body select#company-id');
	var companId = fldCompany.children('option:selected').val();// 这就是selected的值
	$('#div-user-password').show();
};

function user_update() {
	var $table = $('table#production-reg-table');
	var selections = $table.bootstrapTable('getSelections');
	$('#factory-manage-title').html("修改用户信息");
	$('#div-user-password').hide();
	if (selections.length == 0) {
		systemAlert('请选择要修改的记录!');
	} else if (selections.length > 1) {
		systemAlert('修改请一条一条来,不支持批量修改!');
	} else {
		var rec = selections[0];
		var modalWin = $('div#user-reg-window');

		modalWin.find(".modal-body input#reg-id").val(rec.id);
		modalWin.find(".modal-body input#user-name").val(rec.name);
		modalWin.find(".modal-body input#user-description")
				.val(rec.description);
		modalWin.find(".modal-body select#factory-type option[value='"
				+ rec.type + "']").attr("selected", true);
		modalWin.find(".modal-body select#company-id option[value='"
				+ rec.companyId + "']").attr("selected", true);
		modalWin.find(".modal-body select#company-id ").val(rec.companyId);

		var s = rec.rights.split(",");
		$("input[name='rights']").each(function() {
					var i = 0;
					for (var int = 0; int < s.length; int++) {
						if ($(this).attr('rightName') === s[int]) {
							$(this).attr("checked", true);
							i = 1;
							break;
						}
					}
					if (i === 0) {
						$(this).attr("checked", false);
					}
				});

		 for (var int = 0; int < s.length; int++) {
		 $("input:checkbox[value='" + s[int] + "']").attr('checked', 'true');
		 }
		modalWin.modal('show');
	}

};

function userAdd() {
	var modalWin = $('div#user-reg-window');
	var id = modalWin.find(".modal-body input#reg-id").val();
	if (id != null && id > 0) {
		userEdit();
		return;
	}
	var saveBtn = modalWin.find(".modal-footer button#save-btn");
	saveBtn.attr('disabled', "disabled");

	var name = $.trim(modalWin.find(".modal-body input#user-name").val());
	var companyId = modalWin.find(".modal-body select#company-id").val();
	var password = $.trim(modalWin.find(".modal-body input#user-password")
			.val());
	var rights = $('input[id="checkbox_id"]:checked').map(function() {
				return this.value;
			}).get().join();
	var description = modalWin.find(".modal-body input#user-description").val();

	if (name == '') {
		waringFormError(modalWin, '账号不可为空');
		saveBtn.removeAttr('disabled');
		return;
	}

	if (name.length > 20 || name.length < 6) {
		waringFormError(modalWin, '账号长度为6~20位');
		saveBtn.removeAttr('disabled');
		return;
	}

	if (password == '') {
		waringFormError(modalWin, '密码不可为空');
		saveBtn.removeAttr('disabled');
		return;
	}

	if (password.length > 20 || password.length < 6) {
		waringFormError(modalWin, '密码长度为6~20位');
		saveBtn.removeAttr('disabled');
		return;
	}

	$.ajax({
				url : 'user/add.htm',
				type : 'post',
				dataType : 'json',
				data : {
					name : name,
					password : password,
					description : description,
					companyId : companyId,
					status : '1',
					rights : rights
				},
				cache : false,
				success : function(data) {
					if (data.errors.length == 0) {
						saveBtn.removeAttr('disabled');
						modalWin.modal('hide');
						refreshUserTable();
					} else {
						saveBtn.removeAttr('disabled');
						waringFormError(modalWin, data.errors[0].msg);
					}
				},
				error : function(jqXHR) {
					if (jqXHR.responseJSON.errors.length == 0) {
						waringFormError(modalWin, "表单提交失败");
						saveBtn.removeAttr('disabled');
					} else {
						saveBtn.removeAttr('disabled');
						waringFormError(modalWin,
								jqXHR.responseJSON.errors[0].msg);
					}
				}
			});
};

/**
 * 刷新用户管理Table(为了兼容IE下的Modal对话框)
 */
function refreshUserTable() {
	$('table#production-reg-table').bootstrapTable('refresh')
}

function userEdit() {
	var modalWin = $('div#user-reg-window');
	var saveBtn = modalWin.find(".modal-footer button#save-btn");
	saveBtn.attr('disabled', "disabled");
	var id = modalWin.find(".modal-body input#reg-id").val();
	var name = modalWin.find(".modal-body input#user-name").val();
	var companyId = modalWin.find(".modal-body select#company-id").val();
	var password = modalWin.find(".modal-body input#user-password").val();
	var rights = $('input[id="checkbox_id"]:checked').map(function() {
				return this.value;
			}).get().join();
	var description = modalWin.find(".modal-body input#user-description").val();

	if (name == '' || companyId == '' || id == "") {
		waringFormError(modalWin, '表单中存在为空的数据项');
		saveBtn.removeAttr('disabled');
		return;
	}

	$.ajax({
				url : 'user/edit.htm',
				type : 'post',
				dataType : 'json',
				data : {
					id : id,
					name : name,
					companyId : companyId,
					status : '1',
					rights : rights,
					description : description
				},
				cache : false,
				success : function(data) {
					if (data.errors.length == 0) {
						saveBtn.removeAttr('disabled');
						modalWin.modal('hide');
						refreshUserTable();
					} else {
						saveBtn.removeAttr('disabled');
						waringFormError(modalWin, data.errors[0].msg);
					}
				},
				error : function(jqXHR) {
					if (jqXHR.responseJSON.errors.length == 0) {
						waringFormError(modalWin, "表单提交失败");
						saveBtn.removeAttr('disabled');
					} else {
						saveBtn.removeAttr('disabled');
						waringFormError(modalWin,
								jqXHR.responseJSON.errors[0].msg);
					}
				}
			});
};

function user_del() {
	var $table = $('table#production-reg-table');
	var selections = $table.bootstrapTable('getSelections');
	if (selections.length == 0) {
		systemAlert('请选择要删除的记录!');
	} else {
		systemconfirm("确认要删除吗?", "javascript:userDel();");
	}
}

function userDel() {
	var $table = $('table#production-reg-table');
	$('div#system-confirm-window').modal('hide');
	var selections = $table.bootstrapTable('getSelections');
	if (selections.length == 0) {
		systemAlert('请选择要删除的记录!');
	} else {
		var ids = "";
		for (var int = 0; int < selections.length; int++) {
			var rec = selections[int];
			ids = ids + rec.id + ",";
		}
		ids = ids.substring(0, ids.length - 1);
		$.ajax({
					url : 'user/del.htm',
					type : 'post',
					dataType : 'json',
					data : {
						ids : ids
					},
					cache : false,
					success : function(data) {
						if (data.errors.length == 0) {
							systemAlert('删除成功!');
							refreshUserTable();
						} else {
							systemAlert('删除失败!');
						}
					},
					error : function() {
						systemAlert('删除失败!');
					}
				});
	}
};

/**
 * 系统参数状态列的渲染器
 * 
 * @param value
 * @param row
 */
function paramStatusFormatter(value, row) {
	if (value === 0) {
		return '无效';
	} else if (value === 1) {
		return '有效';
	}
};

function systemconfirm(confirmContent, functionName) {
	$('div#system-confirm-window .modal-body span').html(confirmContent);
	$('div#system-confirm-window').modal('show');
	$('#confirmBtn').attr('href', functionName);
};

// TODO need delete
function exportRecord() {
	$.ajax({
				url : 'warehouse/exportReport.htm',
				type : 'post',
				dataType : 'json',
				// data : {
				// groupName : groupName,
				// taskName : taskName,
				// remark : remark,
				// scheduler : scheduler,
				// status : status
				// },
				cache : false,
				success : function(data) {
					// if (data.errors.length == 0) {
					// saveBtn.removeAttr('disabled');
					// modalWin.modal('hide');
					// $('table#task-config-table').bootstrapTable('refresh');
					// } else {
					// saveBtn.removeAttr('disabled');
					// waringFormError(modalWin, data.errors[0].msg);
					// }
				},
				error : function(jqXHR) {
					// if (jqXHR.responseJSON.errors.length == 0) {
					// waringFormError(modalWin, "表单提交失败");
					// saveBtn.removeAttr('disabled');
					// } else {
					// saveBtn.removeAttr('disabled');
					// waringFormError(modalWin,
					// jqXHR.responseJSON.errors[0].msg);
					// }
				}
			});
}

function damage_tag_add() {
	var modalWin = $('div#damage-tag-replace-windows');
	$('#damage-tag-replace-title').html("破损标签替换");
	modalWin.modal('show');
};

// 破损标签
function damageTagReplaceSave() {

	var modalWin = $('div#damage-tag-replace-windows');
	var saveBtn = modalWin.find(".modal-footer button#save-btn");
	saveBtn.attr('disabled', "disabled");

	var packageTag = $.trim(modalWin.find(".modal-body input#pacakge-tag").val());
	var damageBottleTag = $.trim(modalWin.find(".modal-body input#damage-bottle-tag").val());
	var replaceBottleTag = modalWin.find(".modal-body input#replace-bottle-tag").val();

	if (packageTag == '') { 
		waringFormError(modalWin, '箱标序号不能为空。');
		saveBtn.removeAttr('disabled');
		return;
	} else if(isNaN(parseInt(packageTag,10))) {
		waringFormError(modalWin, '箱标序号只能为数字。');
		saveBtn.removeAttr('disabled');
		return;
	}

	if (damageBottleTag == '') {
		waringFormError(modalWin, '破损瓶标序号不能为空。');
		saveBtn.removeAttr('disabled');
		return;
	} else if(isNaN(parseInt(damageBottleTag,10))) {
		waringFormError(modalWin, '破损瓶标序号只能为数字。');
		saveBtn.removeAttr('disabled');
		return;
	}

	if (replaceBottleTag == '') {
		waringFormError(modalWin, '替换瓶标序号不能为空。');
		saveBtn.removeAttr('disabled');
		return;
	} else if(isNaN(parseInt(replaceBottleTag,10))) {
		waringFormError(modalWin, '替换瓶标序号只能为数字。');
		saveBtn.removeAttr('disabled');
		return;
	}

	$.ajax({
				url : 'tag/replace/damage.htm',
				type : 'post',
				dataType : 'json',
				data : {
					packageTag : packageTag,
					damageTag : damageBottleTag,
					replaceTag : replaceBottleTag
				},
				cache : false,
				success : function(data) {
					if (data.errors.length == 0) {
						saveBtn.removeAttr('disabled');
						modalWin.modal('hide');
						refreshTagDamageReplacementTable();
					} else {
						saveBtn.removeAttr('disabled');
						waringFormError(modalWin, data.errors[0].msg);
					}
				},
				error : function(jqXHR) {
					if (jqXHR.responseJSON.errors.length == 0) {
						waringFormError(modalWin, "表单提交失败");
						saveBtn.removeAttr('disabled');
					} else {
						saveBtn.removeAttr('disabled');
						waringFormError(modalWin,
								jqXHR.responseJSON.errors[0].msg);
					}
				}
			});

}

function refreshTagDamageReplacementTable() {
	$('table#damage-tag-reg-table').bootstrapTable('refresh');
}

/*******************************************************
 * 						库存报表
 ******************************************************/
function queryInventoryReportByProduct() {
	var keyword = $('input#keyword').val(); // 查询关键字

	var url = ctx + '/warehouse/inventory.htm';

	// 刷新表格
	$('table#inventory-report-table').bootstrapTable('refresh', {
				url : url,
				query : {
					'keyword' : keyword
				}
			});
};

function inventoryReportOperateFormatter(value, row, index) {
	return [
			'<a class="checkDetail" href="javascript:void(0)" title="查看详细日志">',
			'查看详细日志', '</a>  ' ].join('');
};

function inventoryReportDetail(row) {
	var modalWin = $('div#inventory-report-detail-windows');
	modalWin.modal('show');
	
	$("#inventory-report-detail-table").bootstrapTable(
		'refresh',
		{
			url : ctx
			+ "/warehouse/inventoryDetail.htm?time="
			+ new Date()
					.toLocaleTimeString(),
			query : {
				'productId' : row.productId
			}
		});
};

function inventoryCountFormatter(value, row) {
	if (value === 0) {
		return '-';
	} else {
		return value;
	}
};
