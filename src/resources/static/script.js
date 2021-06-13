//ajaxの処理をやって成功したら最後に必ず呼ぶ
function set_date_href_script(){
	jQuery( function($) {
	    $('tbody td[data-href]').addClass('clickable').click( function() {
	        window.location = $(this).attr('data-href');
	    }).find('a').hover( function() {
	        $(this).parents('td').unbind('click');
	    }, function() {
	        $(this).parents('td').click( function() {
	            window.location = $(this).attr('data-href');
	        });
	    });
	});

	jQuery( function($) {
	    $('tbody th[data-href]').addClass('clickable').click( function() {
	        window.location = $(this).attr('data-href');
	    }).find('a').hover( function() {
	        $(this).parents('th').unbind('click');
	    }, function() {
	        $(this).parents('th').click( function() {
	            window.location = $(this).attr('data-href');
	        });
	    });
	});

	jQuery( function($) {
	    $('body div[data-href]').addClass('clickable').click( function() {
	        window.location = $(this).attr('data-href');
	    }).find('a').hover( function() {
	        $(this).parents('div').unbind('click');
	    }, function() {
	        $(this).parents('div').click( function() {
	            window.location = $(this).attr('data-href');
	        });
	    });
	});
}

function load_talk_list_next() {
	var paramUrl = $("#talk_list_next").attr("ajax-href") + '?' + "startIndex=" + $("#talk_list_next").attr("ajax-index");

	// トークリストに関するfragment(html)取得
	$.ajax({
		type : "GET",
		url : paramUrl,
		dataType : "html",
		success : function(data, status, xhr) {
			$('#talk_list_next').remove();
			
			var  element = document.querySelector('#talk_list');
			element.insertAdjacentHTML('beforeend', data);
			
			set_date_href_script();
		},
		error : function(XMLHttpRequest, status, errorThrown) {
			// エラー処理 何もしない
		}
	});
}

function load_talk_list_back() {
	var paramUrl = $("#talk_list_back").attr("ajax-href") + '?' + "startIndex=" + $("#talk_list_back").attr("ajax-index");

	// トークリストに関するfragment(html)取得
	$.ajax({
		type : "GET",
		url : paramUrl,
		dataType : "html",
		success : function(data, status, xhr) {
			$('#talk_list_back').remove();
			
			var  element = document.querySelector('#talk_list');
			element.insertAdjacentHTML('afterbegin', data);
			
			set_date_href_script();
		},
		error : function(XMLHttpRequest, status, errorThrown) {
			// エラー処理 何もしない
		}
	});
}

function load_see_side(){
	var getUrl = $("#see_side").attr("ajax-href");
	console.log(getUrl);
	
	$.ajax({
		type : "GET",
		url : getUrl,
		dataType : "html",
		success : function(data, status, xhr) {
			var  element = document.querySelector('#see_side');
			element.insertAdjacentHTML('afterbegin', data);
			
			set_date_href_script();
		},
		error : function(XMLHttpRequest, status, errorThrown) {
			// エラー処理 何もしない
		}
	});
}

//最初に実行するもの
function init_example_page(){
	load_see_side();
}