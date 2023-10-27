jQuery(function ($) {
    $(document).ready(function () {


        let init = async () => {
            let data = await getData("");
        }
        let userId = document.getElementById("id").value;
        let getData = (url) => {
            if (url === "" || url === null) {
                url = `/api/task-assign/${userId}`;
            }
            $.ajax({
                url: url,
                contentType: 'application/json',
                dataType: 'json',
                success: function (result) {
                    mapTable(result.data);
                    paging(result.totalPages, result.currentPage, result.limit);
                },
            });
        }

        let paging = (totalPages, currentPage, limit) => {
            $(function () {
                window.pagObj = $('#pagination').twbsPagination({
                    totalPages: totalPages,
                    visiblePages: limit,
                    startPage: currentPage,
                    onPageClick: function (event, page) {
                        if (currentPage !== page) {
                            getData(`/api/task-assign/${userId}?page=${page}&limit=${limit}`);
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
                row += `<td>${v.title}</td>`
                row += `<td>${v.content}</td>`
                row += `<td>${v.deadlineStart}</td>`
                row += `<td>${v.deadlineEnd}</td>`
                row += `<td>${v.createdBy}</td>`
                row += `<td>${v.status}</td>`
                row += `<c:url var="updateTaskURL" value="/admin-permission-edit">
                                                        <c:param name="id" value="${v.id}"/>
                                                    </c:url>
                                                    <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
                                                       title="Cập nhật quyền" href='/admin-permission-edit'>
                                                        <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                                                    </a>`
                row += `</tr>`
            })
            $('#listGroup').empty();
            $('#listGroup').append(row);
        }

        $(".alert").delay(2000).slideUp(200, function () {
            $(this).alert('close');
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
                    var dataArray = $('tbody input[type=checkbox]:checked').map(function () {
                        return $(this).val();
                    }).get();
                    data['ids'] = dataArray;
                    deleteUser(data);
                }
            });
        }

        function deleteUser(data) {
            $.ajax({
                url: '/api/permission',
                type: 'DELETE',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function (result) {
                    window.location.href = '/admin-group-list?message=delete_success';
                },
                error: function (error) {
                    window.location.href = '/admin-group-list?message=error_system';
                },
            });
        }

        function addGroup(data) {
            $.ajax({
                url: '/api/permission-group/create',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function (result) {
                    window.location.href = '/admin-permission-list?message=insert_success';
                },
                error: function (error) {
                    window.location.href = '/admin-permission-list?message=error_system';
                },
            });
        }

        init();
    })
});