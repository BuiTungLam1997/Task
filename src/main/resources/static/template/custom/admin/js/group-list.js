jQuery(function ($) {
    $(document).ready(function () {


        let init = async () => {
            let data = await getData("");
            let dataGroup = await getDataGroup(`/api/group/list`)
        }
        let getData = (url) => {
            let groupId = document.getElementById("id").value;
            if (url === "" || url === null) {
                url = `/api/group/list-permission/${groupId}`;
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
                row += `<td>${v.id}</td>`
                row += `<td>${v.name}</td>`
                row += `<td>${v.code}</td>`
                row += `<td>${v.note}</td>`
                row += `<c:url var="updateTaskURL" value="/admin-permission-edit">
                                                        <c:param name="id" value="${v.id}"/>
                                                    </c:url>
                                                    <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
                                                       title="Cập nhật quyền" href='/admin-permission-edit'>
                                                        <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                                                    </a>`
                row += `</tr>`
            })
            $('#listPermission').empty();
            $('#listPermission').append(row);
        }

        let getDataGroup = (url) => {
            if (url === "" || url === null) {
                url = `/api/group/list`;
            }
            $.ajax({
                url: url,
                contentType: 'application/json',
                dataType: 'json',
                success: function (result) {
                    let html = ``;
                    $.each(result.data, function (i, v) {
                        html += `<option value="${v.id}">${v.name}</option>`
                    });
                    $('#groupId').empty();
                    $('#groupId').append(html);
                },
            });
        }

        $(".alert").delay(2000).slideUp(200, function () {
            $(this).alert('close');
        });

        function fun() {
            $('#search').val();
            $('#btnSearch').submit();
        }

        $('#btnAdd').click(function (e) {
            e.preventDefault();
            const formData = $('#formGroup').serializeArray();
            const data = {};
            $.each(formData, function (i, v) {
                data["" + v.name + ""] = v.value;
            });
            const dataArray = $('tbody input[type=checkbox]:checked').map(function () {
                return $(this).val();
            }).get();
            data['permissionIds'] = dataArray;
            addGroup(data);

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
                    var data = {};
                    var dataArray = $('tbody input[type=checkbox]:checked').map(function () {
                        return $(this).val();
                    }).get();
                    data['ids'] = dataArray;
                    deleteDevice(data);
                }
            });
        }

        function deleteDevice(data) {
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