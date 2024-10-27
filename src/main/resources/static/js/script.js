$(function(){

    // User Register validation
    var $userRegister = $("#userRegister");

    $userRegister.validate({
        rules: {
            name: {
                required: true,
                lettersonly: true
            },
            email: {
                required: true,
                email: true
            },
            mobileNumber: {
                required: true,
                numericOnly: true,
                minlength: 10,
                maxlength: 12
            },
            password: {
                required: true,
                noSpace: true
            },
            confirmpassword: {
                required: true,
                noSpace: true,
                equalTo: '#pass'
            },
            address: {
                required: true,
                validAddress: true
            },
            city: {
                required: true,
                lettersonly: true
            },
            province: {
                required: true
            },
            pincode: {
                required: true,
                numericOnly: true
            },
            img: {
                required: true
            }
        },
        messages: {
            name: {
                required: 'Vui lòng nhập tên.',
                lettersonly: 'Tên chỉ chứa các ký tự chữ cái.'
            },
            email: {
                required: 'Vui lòng nhập email.',
                email: 'Email không hợp lệ.'
            },
            mobileNumber: {
                required: 'Vui lòng nhập số điện thoại.',
                numericOnly: 'Số điện thoại chỉ chứa các số.',
                minlength: 'Số điện thoại phải có ít nhất 10 chữ số.',
                maxlength: 'Số điện thoại không được quá 12 chữ số.'
            },
            password: {
                required: 'Vui lòng nhập mật khẩu.',
                noSpace: 'Mật khẩu không được chứa khoảng trắng.'
            },
            confirmpassword: {
                required: 'Vui lòng xác nhận mật khẩu.',
                noSpace: 'Mật khẩu không được chứa khoảng trắng.',
                equalTo: 'Mật khẩu xác nhận không khớp.'
            },
            address: {
                required: 'Vui lòng nhập địa chỉ.',
                validAddress: 'Địa chỉ không hợp lệ.'
            },
            city: {
                required: 'Vui lòng nhập thành phố.',
                lettersonly: 'Thành phố chỉ chứa các ký tự chữ cái.'
            },
            province: {
                required: 'Vui lòng chọn tỉnh.'
            },
            pincode: {
                required: 'Vui lòng nhập mã pin.',
                numericOnly: 'Mã pin chỉ chứa các số.'
            },
            img: {
                required: 'Vui lòng chọn hình ảnh.'
            }
        }
    });

    // Orders Validation
    var $orders = $("#orders");

    $orders.validate({
        rules: {
            firstName: {
                required: true,
                lettersonly: true
            },
            lastName: {
                required: true,
                lettersonly: true
            },
            email: {
                required: true,
                email: true
            },
            mobileNo: {
                required: true,
                numericOnly: true,
                minlength: 10,
                maxlength: 12
            },
            address: {
                required: true,
                validAddress: true
            },
            city: {
                required: true,
                lettersonly: true
            },
            province: {
                required: true
            },
            pincode: {
                required: true,
                numericOnly: true
            },
            paymentType: {
                required: true
            }
        },
        messages: {
            firstName: {
                required: 'Vui lòng nhập tên.',
                lettersonly: 'Tên chỉ chứa các ký tự chữ cái.'
            },
            lastName: {
                required: 'Vui lòng nhập họ.',
                lettersonly: 'Họ chỉ chứa các ký tự chữ cái.'
            },
            email: {
                required: 'Vui lòng nhập email.',
                email: 'Email không hợp lệ.'
            },
            mobileNo: {
                required: 'Vui lòng nhập số điện thoại.',
                numericOnly: 'Số điện thoại chỉ chứa các số.',
                minlength: 'Số điện thoại phải có ít nhất 10 chữ số.',
                maxlength: 'Số điện thoại không được quá 12 chữ số.'
            },
            address: {
                required: 'Vui lòng nhập địa chỉ.',
                validAddress: 'Địa chỉ không hợp lệ.'
            },
            city: {
                required: 'Vui lòng nhập thành phố.',
                lettersonly: 'Thành phố chỉ chứa các ký tự chữ cái.'
            },
            province: {
                required: 'Vui lòng chọn tỉnh.'
            },
            pincode: {
                required: 'Vui lòng nhập mã pin.',
                numericOnly: 'Mã pin chỉ chứa các số.'
            },
            paymentType: {
                required: 'Vui lòng chọn phương thức thanh toán.'
            }
        }
    });

    // Reset Password Validation
    var $resetPassword = $("#resetPassword");

    $resetPassword.validate({
        rules: {
            password: {
                required: true,
                noSpace: true
            },
            confirmPassword: {
                required: true,
                noSpace: true,
                equalTo: '#pass'
            }
        },
        messages: {
            password: {
                required: 'Vui lòng nhập mật khẩu.',
                noSpace: 'Mật khẩu không được chứa khoảng trắng.'
            },
            confirmPassword: {
                required: 'Vui lòng xác nhận mật khẩu.',
                noSpace: 'Mật khẩu không được chứa khoảng trắng.',
                equalTo: 'Mật khẩu xác nhận không khớp.'
            }
        }
    });

    // Custom validator methods
    jQuery.validator.addMethod('lettersonly', function(value, element) {
        return /^[\p{L}\s]+$/u.test(value); // Cho phép chữ Unicode và khoảng trắng
    }, 'Chỉ được chứa các ký tự chữ cái.');

    jQuery.validator.addMethod('noSpace', function(value, element) {
        return value.trim().length === value.length; // Không cho phép khoảng trắng ở đầu hoặc cuối
    }, 'Không được chứa dấu cách ở đầu hoặc cuối.');

    jQuery.validator.addMethod('validAddress', function(value, element) {
        return /^[\p{L}0-9\s,.-]+$/u.test(value); // Cho phép ký tự Unicode, số, dấu câu, khoảng trắng
    }, 'Địa chỉ không hợp lệ.');

    jQuery.validator.addMethod('numericOnly', function(value, element) {
        return /^[0-9]+$/.test(value); // Chỉ cho phép các ký tự số
    }, 'Chỉ được chứa các chữ số.');
});
