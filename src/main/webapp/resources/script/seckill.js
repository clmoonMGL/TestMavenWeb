/**
 * 存放主要交互逻辑的js代码
 * javascript交互逻辑模块化
 * */

var seckill = {
    /*封装秒杀相关ajax的url*/
    URL: {
    	now : function(){
    		return '/seckill/time/now';
    	}
    },
    //验证手机号
    validatePhone: function (phone) {
        //直接判断对象会看对象是否为空,空就是undefine就是false; isNaN 非数字返回true
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    countdown: function(seckillId,nowTime,startTime,endTime){
    	var seckillBox = $('#seckill-box');
    	//时间判断
    	if(nowTime > endTime){
    		seckillBox.html('秒杀结束!');
    	}else if(nowTime < startTime){
    		//秒杀未开始，计时事件绑定
    		var killTime = new Date(startTime + 1000);
    		seckillBox.countdown(killTime,function(event){
    			//时间格式
    			var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
    			seckillBox.html(format);
    		});
    	}else{
    		//秒杀开始
    	}
    },
    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            // 用户手机验证和登录，计时交互操作
            // 规划交互流程
            // 在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            //验证手机号 返回false 显示弹出层
            if (!seckill.validatePhone(killPhone)) {
                //绑定手机号
                var killPhoneModal = $('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    show: true,// 显示弹出层
                    backdrop: 'static', //禁止位置关闭
                    keyboard: false // 关闭键盘事件
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    console.log('inputPhone='+inputPhone);//TODO
                    if (seckill.validatePhone(inputPhone)) {
                        // 电话号码写入cookie当中
                        $.cookie('killPhone', inputPhone, {expires: 7});
                        //刷新页面
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<lable class="label label-danger">手机号错误</lable>').show(300);
                    }
                });
            }
            //已经登录
            //计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(),{},function(result){
            	if(result && result['success']){
            		var nowTime = result['data'];
            		//时间判断，计时交互
            		seckill.countdown(seckillId,nowTime,startTime,endTime);
            	}else{
            		console.log('result : ' + result);
            	}
            });
        }
    }
}