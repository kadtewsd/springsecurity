function outputCookie() {
   var theCookies = document.cookie.split(';');
   var aString = '';
   console.log("cookies are ... ")
   for (var i = 1 ; i <= theCookies.length; i++) {
       aString +=theCookies[i-1] + "¥n";
   }
   $("#cookie").val(theCookies);
}

function outputHeaders(xhr) {
    $("#header").val(xhr.getAllResponseHeaders());
}

$(function() {
    $( '#ajax-button' ) .click(
    function() {
        var username = $("#username").val();
        var password = $("#password").val();
         var data = {
            username: username,
            password: password
        };
        var hostUrl= $("#requestUrl").val();
        console.log("request begins.. data is "+ JSON.stringify(data) + " URL is " + hostUrl);
        $.ajax({
            url: hostUrl,
            type:'post',
            dataType: 'JSON', // AuthenticationSuccessHandler にて JSON を戻します。
//            scriptCharset: 'utf-8',
//            data : JSON.stringify(data),
            //data : JSON.stringify({username: username, password: password}),
            data : data,
            timeout:10000,
//           jsonp: 'callback',
        }).then(function(data, status, xhr) {
            var d = new $.Deferred();
            outputHeaders(xhr);
            var json = JSON.stringify(data);
            $("#json").val(json);
            outputCookie();
            d.resolve();
            return d.promise();
        }).done(function(data) {
            console.log("response status is ok... response data is rendered");
        }).fail(function(XMLHttpRequest, textStatus, errorThrown) {
                 console.log("NG... cause is " + textStatus + " errorThrown" + errorThrown);
        })
    });
});
