$(document).ready(function () {
    let btn = $(".toggle-btn");
    $(btn).click(function () {
        if (btn.html() === '회원목록 펼치기') {
            // 회원목록을 가져오는 코드!!
            $.ajax({
                type: "GET",
                url: "/membersJson",
                dataType: "JSON",

                success:function (data) {
                    viewMemberList(data);
                },
                error: function (error) {
                    alert(error);
                }
            });
            btn.html("회원목록 접기");
        } else {
            $(".members").html("");
            btn.html("회원목록 펼치기");
        }
    })

    function viewMemberList(data) {
        $(".members").empty();

        $.each(data, function (i, item) {
            let tr = $("<tr></tr>");

            $.each(item, function (key, value){
                console.log(key, value);
                $("<td></td>").html(value).appendTo(tr);
            });
            $(".members").append(tr);
        });
    };

})