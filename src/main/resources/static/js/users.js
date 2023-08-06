
//start
$(document).ready(function(){
    getAuthorizeUser()
    getAllUser()
})
const allRoles = fetch('/admin/roles')
    .then(response => response.json())

//заполнение выбора роли
allRoles.then(e => {
    let cod = ``
    for (const i in e) {
        cod += `<option value = ${e[i].id}>${e[i].name.replace("ROLE_", "")}</option>`
    }
    document.getElementById('rolesNewUser').innerHTML = cod;
})

//getResponses
function getAllUser(){
    $.ajax({
        type: 'get',
        url: '/admin/users',
        response: 'json',
        success: function (data){
            setAdminTable (data)
        }
    })
}

function getAuthorizeUser (){
    $.ajax({
        type: 'get',
        url: '/admin/currentUser',
        response: 'json',
        success: function (data){
            setRolePanel(data);
        }
    })
}

// Add user
$(document).on("click", "#btnNewUser", function () {
    $('#table-tab').trigger('click');
    let user = $("#formAddUser").serializeArray();
    $('#nameNewUser').val('');
    $('#lastNameNewUser').val('');
    $('#ageNewUser').val('');
    $('#emailNewUser').val('');
    $('#passNewUser').val('');
    $('#rolesNewUser').val('')
    $.ajax({
        type: 'POST',
        url: '/admin/users/new',
        data: user,
        timeout: 3000,
        success: function (){
            getAllUser()
        }
    });
});

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e.target)
        }
    })
}

// table
function setAdminTable(result){
    $('#adminTable').empty()
    let role = '';
    $.each(result, function (i, user) {
        $.each(user.roles, function (i, userRole) {
            role += userRole.name
        })
        $('<tr>').append(
            $('<td>').text(user.id),
            $('<td>').text(user.username),
            $('<td>').text(user.surname),
            $('<td>').text(user.age),
            $('<td>').text(user.email),
            $('<td>').text(role.replace("ROLE_","")),
            $('<td>').append($('<button>').text("Edit").attr({
                "type": "button",
                "class": "btn btn-info edit",
                "data-toggle": "modal",
                "data-target": "#editModal",
            }).data("user", user)),
            $('<td>').append($('<button>').text("Delete").attr({
                "type": "button",
                "class": "btn btn-danger delete",
                "data-toggle": "modal",
                "data-target": "#delModal",
            }).data("user", user))
        ).appendTo('#adminTable')
        role = ''
    })
}

//left Panel
function setRolePanel(authUser){
    let roleStr = ''
    $.each(authUser.roles,function (i,role){
        roleStr += role.name
    })
    if(roleStr.indexOf('ADMIN') >=0){
        $('#adminTab').addClass('active')
        $('#adminPage').addClass('active show')
    }else {
        $('#userTab').addClass('active')
        $('#userPage').addClass('active show')
        $('#adminTab').hide()
    }
    roleStr=''
}

// modal edit
$(document).on('click','.edit',function () {
    let user = $(this).data('user');
    $('#idEditUser').val(user.id);
    $('#nameEditUser').val(user.username);
    $('#lastNameEditUser').val(user.surname);
    $('#ageEditUser').val(user.age);
    $('#emailEditUser').val(user.email);
    $('#passEditUser').val('');
    $('#editRoles').val(user.roles);
})

$(document).on('click','#btnEditUser',function () {
    let user = $('#formEditUser').serializeArray();
    $.ajax({
        type: 'PATCH',
        url: '/admin/users/edit',
        data: user,
        timeout: 3000,
        success: function (){
            $('#editModal').modal('hide')
            getAllUser()
        }
    });
})

//modal  delete
$(document).on('click','.delete',function () {
    let user = $(this).data('user');
    $('#idDelUser').val(user.id);
    $('#nameDelUser').val(user.username);
    $('#lastNameDelUser').val(user.surname);
    $('#ageDelUser').val(user.age);
    $('#usernameDelUser').val(user.email);
    $('#roleDelUser').val(user.role);
})

$(document).on('click','#btnDelUser',function () {
    $.ajax({
        type: 'DELETE',
        url: "admin/users/delete/"+ $('#idDelUser').val(),
        timeout: 3000,
        success: function (){
            $('#delModal').modal('hide')
            getAllUser()
        }
    });
})