jQuery(function ($) {

    $(document).ready(function () {
        let init = async () => {
            let data = await getData("");
        }
        let getData = (url) => {
            if (url === "" || url === null) {
                url = `/api/task-follow/list`;
            }
            $.ajax({
                url: url,
                contentType: 'application/json',
                dataType: 'json',
                success: function (result) {
                    mapTable(result.data);
                },
            });
        }
        let mapTable = (data) => {
            let row = '';
            $.each(data, function (i, v) {
                row += `<tr>`
                row += `<td><input type="checkbox" id="checkbox_${v.id}" value="${v.id}"></td>`
                row += `<td>${v.title}</td>`
                row += `<td>${v.content}</td>`
                row += `<td>${v.deadlineStart}</td>`
                row += `<td>${v.deadlineEnd}</td>`
                row += `<td>${v.status}</td>`
                row += `</tr>`
            })
            $('#listTaskFollow').empty();
            $('#listTaskFollow').append(row);
        }

        $(".alert").delay(2000).slideUp(200, function () {
            $(this).alert('close');
        });
        init();
    })
});