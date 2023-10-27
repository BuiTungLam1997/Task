jQuery(function ($) {
    $(document).ready(function () {
        let init = async () => {
            getData()
        }
        init();
    });
    let getData = async (url) => {
        if (!url) url = `/api/task/list`;
        $.ajax({
            url: url,
            contentType: 'application/json',
            dataType: 'json',
            success: function (result) {
                mapTable(result.data);
                paging(url, result.totalPages, result.currentPage, result.limit);
            },
        });
    }

    let mapTable = (data) => {
        let row = '';
        $.each(data, function (i, v) {
            row += `<tr>`
            row += `<td><input type="checkbox" id="checkbox_${v.id}" value="${v.id}"></td>`
            row += ` <td>${v.title}</td>`
            row += `<td>${v.content}</td>`
            row += `<td>${v.performer}</td>`
            row += `<td>${v.deadlineEnd}</td>`
            row += `<td>${v.status}</td>`
            row += `<td>${v.point}</td>`
            row += `<td>
            <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
                       title="Cập nhật bài viết" href='/admin-task-edit?id=${v.id}'>
            <i class="fa fa-pencil-square-o" aria-hidden="true"/>
           </a>
           
            <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
                      title="Comment" href='/comment?id=${v.id}'>
            <i class="fa fa-fw fa-comment" aria-hidden="true"/>
            </a>

            <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
                      title="Giao công việc" href='/admin-task-giveAJob?id=${v.id}'>
            <i class="fa fa-fw fa-paper-plane-o" aria-hidden="true"/>
           </a>
           
           </td>`
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
    $(".alert").delay(2000).slideUp(200, function () {
        $(this).alert('close');
    });

    $('#btnSearch').click(function (e) {
        $('#search').val();
        $('#btnSearch').submit();
    });

    warningBeforeDelete = () => {
        swal({
            title: "Mài có chắc chắn xóa nó không ?",
            text: "Thấy câu hỏi ở trên không ,ừ chỗ này giống nó đó ,trả lời đi!",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: "btn-success",
            cancelButtonClass: "btn-danger",
            confirmButtonText: "Có, Con đồng ý xóa thưa ngài!",
            cancelButtonText: "Không , Con cần thời gian suy nghĩ!",
            closeOnConfirm: false,
            closeOnCancel: false
        }).then(function (isConfirm) {
            if (isConfirm) {
                var data = {};
                var dataArray = $('tbody input[type=checkbox]:checked').map(function () {
                    return $(this).val();
                }).get();
                data ['ids'] = dataArray;
                deleteDevice(data);
            }
        });
    }

    deleteDevice = (data) => {
        $.ajax({
            url: '${APIurl}',
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                window.location.href = '${TaskURL}&message=delete_success';
            },
            error: function (error) {
                window.location.href = '${TaskURL}&message=error_system';
            },
        });
    }
});