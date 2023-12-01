jQuery(function ($) {
    $(document).ready(function () {

    });

    $('#btnPeriod').click(function (e) {
        let period = document.getElementById('period').value;
        let userId = document.getElementById('id').value;
        let url = `/api/report/user/${userId}/${period}`;
        getData(url);
    });
    let getData = async (url) => {
        $.ajax({
            url: url,
            contentType: 'application/json',
            dataType: 'json',
            success: function (result) {
                let point = 0;
                $.each(result.data, function (i, v) {
                    point += v.point;
                })
                $('#totalPoint').val(point);
                mapTable(result.data);
                paging(url, result.totalPages, result.currentPage, result.limit);
            },
        });
    }

    let mapTable = (data) => {
        let row = '';
        $.each(data, function (i, v) {
            row += `<tr>`
            row += `<td>${v.title}</td>`
            row += `<td>${v.content}</td>`
            row += `<td>${v.deadlineStart}</td>`
            row += `<td>${v.deadlineEnd}</td>`
            row += `<td>${v.createdBy}</td>`
            row += `<td>${v.status}</td>`
            row += `<td>${v.point}</td>`
            row += `</tr>`
        })

        $('#listTask').empty();
        $('#listTask').append(row);
    };
    let paging = (url, totalPages, currentPage, limit) => {
        $(function () {
            window.pagObj = $('#pagination').twbsPagination({
                totalPages: totalPages,
                visiblePages: limit,
                startPage: currentPage,
                onPageClick: function (event, page) {
                    if (currentPage !== page) {
                        getData(`${url}?page=${page}&limit=${limit}`);
                        currentPage = page;
                    }
                }
            });
        });
    };
});