// input에 클릭한 회원의 정보를 저장
$(document).on('click', 'tbody > tr', function () {
    let td_arr = $(this).find("td");

    $("tr").removeClass("active"); // 다른 행이 눌릴 때 기존에 지정된 행을 해제!
    $(this).addClass("active");

    $("#no").val($(td_arr[0]).text());
    $("#id").val($(td_arr[1]).text());
    $("#name").val($(td_arr[2]).text());
    $("#email").val($(td_arr[3]).text());
});

$(document).ready(function () {
    // 회원 정보 수정 AJAX
    $(".update-btn").click(() => {
        let data = $("#form").serializeArray();
        $.ajax({
            type: "post",
            url: "/users/edit",
            data: data
        }).success(() => {
            $.ajax({
                url: "/users",
            }).success((data) => {
                viewListMember(data);
            });
        });
    });


    $(".list-btn").click(function () {
        $.ajax({
            type: "get",
            url: "/users",
            dataType: "json",

            success: function (data) {
                viewListMember(data); // 회원 목록을 그려주는 함수
            },
            error: function (error) {
                console.log(error);
            }
        });
    })

    function viewListMember(data) {
        let memberTable = $(".memberList").empty();

        let tr = $("<tr></tr>");
        let th = $("<th>#</th><th>아이디</th><th>이름</th><th>이메일</th>");

        tr.append(th);
        memberTable.append($("<thead></thead>").append(tr));
        let tbody = $("<tbody></tbody>");
        memberTable.append(tbody);

        $.each(data, function (index, item) {
            let tr = $("<tr></tr>");
            $.each(item, function (key, value) {
                $("<td></td>").html(value).appendTo(tr);
            });

            tbody.append(tr);

        });
    }
});