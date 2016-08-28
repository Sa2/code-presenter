$(function() {

    // Sendボタンにイベントをバインドする
    $('#send-code').on('click', function() {
        sendCode();
    })

    function sendCode() {
        inputCodeToJson();

        if (location.hostname === 'localhost') {
            // for Developing
            var apiUrl = 'http://localhost:9000/presenter/codesend'
        } else {
            // on Azure
            var apiUrl = 'http://code-presenter.japaneast.cloudapp.azure.com:9000/presenter/codesend'
        }

        console.log(inputCodeToJson())
        var data = inputCodeToJson();

        $.ajax({
            url: apiUrl,
            type: 'POST',
            data: data,
            //
            contentType: 'application/json',
            success: function(data, textStatus) {
                // debug code
                if (location.hostname === 'localhost') {
                    console.log(data);
                    console.log(textStatus);
                    console.log(location.hostname);
                }
            },
            dataType: 'json'
        });
    };

    //
    function inputCodeToJson() {
        var json = {};
        json.id = 1
        json.uuid = "123123"
        json.refKey = "34563456"
        json.content = $("#input-code").val();
        json.destination = 0
        return JSON.stringify(json);
    };
});