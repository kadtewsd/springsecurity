function outputCookie() {
   var theCookies = document.cookie.split(';');
   var aString = '';
   console.log("cookies are ... ")
   for (var i = 1 ; i <= theCookies.length; i++) {
       console.log(theCookies[i-1]);
   }
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
        }).done(function(data) {
                 console.log("response status is ok... response data is below");
                 console.log(JSON.stringify(data));
                 outputCookie();
        }).fail(function(XMLHttpRequest, textStatus, errorThrown) {
                 console.log("NG... cause is " + textStatus + " errorThrown" + errorThrown);
        })
    });
});
