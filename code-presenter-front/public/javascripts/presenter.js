$(function() {

    // 送信
    $('#send-code').on('click', function() {
        console.log("test");
        sendCode();
    })

    function sendCode() {
        var url = 'localhost:9000/presenter/codesend'
        var data = '{}'
        $.post(
            url,
            data,
            function(data, textStatus) {
                console.log(data)
                console.log(textStatus)
            },
            'json'
        )
    }
});