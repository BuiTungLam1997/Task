jQuery(function ($) {
    $(document).ready(function () {
        let init = async () => {
            getData()
        }
        init();
    });
    let APIUrl = `/api/user`;
    let UserGroupAPIUrl = `api/user-group`;
    let UserURL = `/admin-user-list`;
    let getData = (url) => {
        if (!url) url = `/api/user/list`;
        $.ajax({
            url: url,
            type: 'GET',
            contentType: 'application/json',
            dataType: 'json',
            success: function (result) {
                mapTable(result.data);
                paging(url, result.totalPages, result.currentPage, result.limit);
                responseMessage(result.status, result.message);
            },
        });
    }

    let responseMessage = (status, message) => {
        let row = ``;
        if (status === '200') {
            row += `<div class="alert alert-success">`;
            row += ` <a>${message}</a>`;
            row += `</div>`;
        } else if (status !== '200') {
            row += `<div class="alert alert-danger">`;
            row += `<a>${message}</a>`;
            row += `</div>`;
        }
        $('#message').empty();
        $('#message').append(row);

    }
    let mapTable = (data) => {
        let row = '';
        $.each(data, function (i, v) {
            row += `<tr>`
            row += ` <td><input type="checkbox" id="checkbox_${v.id}"
                               value="${v.id}">`
            row += `</td>`
            row += `<td>${v.id}</td>`
            row += `<td>${v.username}</td>`
            row += `<td>${v.fullName}</td>`
            row += `<td>${v.status}</td>`
            row += `<td>${v.createdDate}</td>`
            row += `<td>${v.createdBy}</td>`
            row += `<td>${v.email}</td>`
            row += `<td>
                        <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
                           title="Cập nhật user" href='/admin-user-edit?id=${v.id}'><i
                            class="fa fa-pencil-square-o" aria-hidden="true"></i>
                        </a>                   
                        <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
                           title="Báo cáo công việc user" href='/admin-report-user?id=${v.id}'>
                            <i class="fa fa-clipboard" aria-hidden="true"></i>
                        </a>

                    </td>`
            row += `</tr>`
        })
        $('#listUser').empty();
        $('#listUser').append(row);
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

    $('#btnAdd').click(function (e) {
        e.preventDefault();
        const formData = $('#formGroup').serializeArray();
        const data = {};
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        data ['userIds'] = $('tbody input[type=checkbox]:checked').map(function () {
            return $(this).val();
        }).get();
        addGroup(data);

    });

    function fun() {
        $('#search').val();
        $('#btnSearch').submit();
    }

    function warningBeforeDelete() {
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
                data ['ids'] = $('tbody input[type=checkbox]:checked').map(function () {
                    return $(this).val();
                }).get();
                deleteDevice(data);
            }
        });
    }

    function deleteDevice(data) {
        $.ajax({
            url: APIUrl,
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                window.location.href = UserURL + '&message=delete_success';
            },
            error: function (error) {
                window.location.href = UserURL + '&message=error_system';
            },
        });
    }

    function addGroup(data) {
        $.ajax({
            url: UserGroupAPIUrl,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                responseMessage(result.status, result.message);
            },
            error: function (error) {
                responseMessage(`400`, `Error`);
            },
        });
    }
});