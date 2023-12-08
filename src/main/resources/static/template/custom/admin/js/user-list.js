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

        init();
    })
});