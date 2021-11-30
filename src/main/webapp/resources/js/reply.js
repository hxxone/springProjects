console.log("reply module...")

var replyService = (function () {
		function add(reply, callback, error){
			console.log("add reply .. ");
			$.ajax({
				type:'post',
				url : '/replies/new',
				data : JSON.stringify(reply),
				contentType : "application/json; charset=utf-8",
				success: function(result, status, xhr){
					if(callback) callback(result)
				},
				error : function(xhr, status, er){
					if(error) error(err)
				}
			}) 
		}
		const getList = (param, callback, error) => {
			var bno = param.bno;
			var page = param.page || 1;
			$.getJSON("/replies/pages/"+bno +"/"+ page+".json",
				function(data){
					if(callback) callback(data.replyCnt, data.list)
				}).fail(function(xhr, status, err){
				if(error) error()
			})
		}
		const remove = (rno, replyer, callback, error) => {
			$.ajax({
				type:'delete',
				url:'/replies/'+rno,
				data: JSON.stringify({rno:rno, replyer:replyer}),
				contentType: "application/json; charset=utf-8",
				success: (deleteResult, status, xhr) => {
					if(callback) callback(deleteResult)
				},
				error: (xhr, status, er) => {
					if(error) error(er)
				}
			})
		}
		
	
		
		
		const update=(reply,callback,error)=>{
			console.log("Rno: "+reply.rno);
			
			$.ajax({
				type:'put',
				url:"/replies/"+reply.rno,
				data:JSON.stringify(reply),
				contentType:"application/json; charset=utf-8",
				success:(result,status,xhr)=>{
					if(callback) callback(result);
				},
				error:(xhr,status,er)=>{
					if(error) error(er);
				}
			})
		}
		
		
		
		
		const get = (rno, callback, error) =>{
			$.get("/replies/" + rno +".json", (result)=>{
				if(callback) callback(result)
			}).fail((exr, status, err) => {
				if(error) error();
			})
		}
		
		const displayTime = (timeValue) => {
			var today = new Date();
			var gap = today.getTime() - timeValue;
			var dateObj = new Date(timeValue);
			
			if(gap<1000*60*60*24){
				var hh = String(dateObj.getHours()).padStart(2, '0');
				var mi = String(dateObj.getMinutes()).padStart(2, '0');
				var ss= String(dateObj.getSeconds()).padStart(2, '0');
				return [ hh, mi, ss].join(':')
			}else{
				var yy=dateObj.getFullYear();
				var mm = String(dateObj.getMonth()+1).padStart(2,'0');
				var dd = String(dateObj.getDate()).padStart(2,'0');
				return [ yy,mm,dd].join('/')
			}
		}
		return {add, getList, remove, update, get, displayTime}	
	})();// 정의와 함께 동시에 호출 IIFE 