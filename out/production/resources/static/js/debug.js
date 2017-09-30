function outputCookie() {
   var theCookies = document.cookie.split(';');
   var aString = '';
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
        console.log(data);
        var hostUrl= 'http://localhost:9000/research/authentication';
        console.log(username + password);
        $.ajax({
            url: hostUrl,
            type:'post',
            dataType: 'JSON',
            scriptCharset: 'utf-8',
            contentType: 'application/json',
//            data : JSON.stringify(data),
            //data : JSON.stringify({username: username, password: password}),
            data : data,
            timeout:10000,
//           jsonp: 'callback',
        }).done(function(data) {
                 console.log("ok");
                 console.log(data);
                 outputCookie();
        }).fail(function(XMLHttpRequest, textStatus, errorThrown) {
                 console.log("NG");
        })
    });
});
