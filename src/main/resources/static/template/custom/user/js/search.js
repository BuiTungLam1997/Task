jQuery(function ($) {
    $(document).ready(function () {
        let APIUrl = "/api/task";
        let init = async () => {
            getData("");
        }
        let getData = (url) => {
            let search = document.getElementById('searchResponse').value;
            if (url === "" || url === null) {
                url = `/api/task-assign/search/${search}`;
            }
            $.ajax({
                url: url,
                type: `GET`,
                contentType: 'application/json',
                dataType: 'json',
                success: function (result) {
                    mapTable(result.data);
                    paging(url, result.totalPages, result.currentPage, result.limit);
                },
            });
        }
        let paging = (url, totalPages, currentPage, limit) => {
            $(function () {
                window.pagObj = $('#pagination').twbsPagination({
                    totalPages: totalPages,
                    visiblePages: limit,
                    startPage: currentPage,
                    onPageClick: function (event, page) {
                        if (currentPage !== page) {
                            getData(url + `?page=${page}&limit=${limit}`);
                            currentPage = page;
                        }
                    }
                });
            });
        }
        let mapTable = (data) => {
            let row = '';
            $.each(data, function (i, v) {
                row += `<tr>`
                row += `<td><input type="checkbox" id="checkbox_${v.id}" value="${v.id}"></td>`
                row += `<td>${v.title}</td>`
                row += `<td>${v.content}</td>`
                row += `<td>${v.deadlineEnd}</td>`
                row += `<td>${v.performer}</td>`
                row += `<td>${v.status}</td>`
                row += `<td>
                            <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
                                title="Comment" href='/comment/${v.id}'>
                                <i class="fa fa-fw fa-comment" aria-hidden="true"></i>
                           </a>
                        </td>`
                row += `</tr>`
            })
            $('#listAssign').empty();
            $('#listAssign').append(row);
        }

        $('#btnSearch').click(function (e) {
            $('#search').val();
            $('#btnSearch').submit();
        });

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
                    let data = {};
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
                    window.location.href = '${UserTaskURL}&message=delete_success';
                },
                error: function (error) {
                    window.location.href = '${UserTaskURL}&message=error_system';
                },
            });
        }

        $(`#btnFollow`).click(function () {
            let data = {};
            data ['taskIds'] = $('tbody input[type=checkbox]:checked').map(function () {
                return $(this).val();
            }).get();
            $.ajax({
                url: `/api/task-follow/create`,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function (result) {
                    window.location.href = '${UserTaskURL}&message=insert_success';
                },
                error: function (error) {
                    window.location.href = '${UserTaskURL}&message=error_system';
                },
            });
        });


        $(".alert").delay(2000).slideUp(200, function () {
            $(this).alert('close');
        });

        init();

    })
});