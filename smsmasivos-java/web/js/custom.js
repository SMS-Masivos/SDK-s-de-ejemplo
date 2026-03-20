$(document).ready(function () {
  $("textarea#textarea1").characterCounter();
  $("select").formSelect();
  $(".tabs").tabs();

  $.ajax({
    url: "aux",
    data: { cmd: "xmls" },
    type: "POST",
    success: (res) => {
      res = JSON.parse(res);
      if (res.code == 0) {
        return;
      }

      if (res.ApiKey != "_") {
        $("#api").val(res.ApiKey);
        $("#api").parent().find("label").addClass("active");
      }
    },
  });
});

$(document).on("click", "li a", function () {
  if ($(this).hasClass("active")) {
    $("#regform").get()[0].reset();
    $("#valform").get()[0].reset();
  }
});

$("#request_credits").on("click", (e) => {
  $(e.currentTarget).addClass("disabled");
  $.ajax({
    url: "aux",
    data: { cmd: "credits" },
    type: "POST",
    success: (res) => {
      res = JSON.parse(res);
      $("#request_credits").removeClass("disabled");
      if (res.success) {
        $("#apiform").get()[0].reset();
        swal({
          title: "Total de cr\u00E9ditos",
          text: "Cr\u00E9ditos disponibles:  " + res.credit,
          icon: "info",
        });
      } else {
        $("#apiform").get()[0].reset();
        swal({
          title: "Mensaje",
          text: res.message,
          icon: "warning",
        });
      }
    },
  });
});

$("#tokenform").validate({
  rules: {
    api: {
      required: true,
    },
  },
  messages: {
    api: {
      required: "Requerido",
    },
  },
  errorElement: "span",
  errorPlacement: function (error, element) {
    var placement = $(element).data("error");
    if (placement) {
      $(placement).append(error);
    } else {
      error.insertAfter(element);
    }
  },
  submitHandler: function () {
    $.ajax({
      url: "aux",
      data: {
        cmd: "auth",
        api: $("#api").val(),
      },
      method: "post",
      success: (res) => {
        res = JSON.parse(res);

        if (res.success) {
          swal({
            title: "Mensaje",
            text: res.message,
            icon: "info",
          });
        } else {
          swal({
            title: "Mensaje",
            text: res.message,
            icon: "warning",
          });
        }
      },
    });
  },
});

$("#apiform").validate({
  rules: {
    destination: {
      required: true,
      minlength: 10,
    },
    content: {
      required: true,
      maxlength: 160,
    },
  },
  messages: {
    destination: {
      required: "Requerido",
      minlength: "M\u00EDnimo 10 d\u00EDgitos",
    },
    content: {
      required: "Requerido",
      maxlength: "No puede exceder 160 caracteres",
    },
  },
  errorElement: "span",
  errorPlacement: function (error, element) {
    var placement = $(element).data("error");
    if (placement) {
      $(placement).append(error);
    } else {
      error.insertAfter(element);
    }
  },
  submitHandler: function () {
    var api = $("#api").val();
    var nombre = $("#nombre").val();
    var sandbox = "";

    if ($("#sandbox:checked").length) {
      sandbox = 1;
    } else {
      sandbox = 0;
    }

    if (api.trim() == "") {
      swal({
        title: "Advertencia",
        text: "Debes introducir y guardar tu ApiKey para poder enviar mensajes",
        icon: "warning",
      });
      return;
    }

    $.ajax({
      url: "aux",
      data: {
        cmd: "send",
        textPais: $("#country option:selected").text(),
        txtNumero: $("#destination").val(),
        txtMensaje: $("#textarea1").val(),
        sandbox: sandbox,
        nombre: nombre,
      },
      method: "post",
      success: (res) => {
        $("#apiform").get()[0].reset();

        swal({
          title: "Mensaje",
          text: res,
          icon: "info",
        });
      },
    });
  },
});

$("#regform").validate({
  rules: {
    destination: {
      required: true,
      minlength: 10,
    },
  },
  messages: {
    destination: {
      required: "Requerido",
      minlength: "M\u00EDnimo 10 d\u00EDgitos",
    },
  },
  errorElement: "span",
  errorPlacement: function (error, element) {
    var placement = $(element).data("error");
    if (placement) {
      $(placement).append(error);
    } else {
      error.insertAfter(element);
    }
  },
  submitHandler: function () {
    var api = $("#api").val();
    var nombre = $("#nombre").val();

    if (api.trim() == "") {
      swal({
        title: "Advertencia",
        text: "Debes introducir y guardar tu ApiKey para poder enviar mensajes",
        icon: "warning",
      });
      return;
    }

    $.ajax({
      url: "aux",
      data: {
        cmd: "2fa_register",
        txtPais: $("#country option:selected").text(),
        txtNumero: $("#destination").val(),
        txtCode: $("input[name=group1]:checked").val(),
        formato: $("input[name=group2]:checked").val(),
        nombre: nombre,
      },
      method: "post",
      success: (res) => {
        $("#regform").get()[0].reset();
        swal({
          title: "Mensaje",
          text: res,
          icon: "info",
        });
      },
    });
  },
});

$("#valform").validate({
  rules: {
    destination_ve: {
      required: true,
      minlength: 10,
    },
    verification: {
      required: true,
      minlength: 4,
    },
  },
  messages: {
    destination_ve: {
      required: "Requerido",
      minlength: "M\u00EDnimo 10 d\u00EDgitos",
    },
    verification: {
      required: "Requerido",
      maxlength: "Deben ser por lo menos 4 caracteres",
    },
  },
  errorElement: "span",
  errorPlacement: function (error, element) {
    var placement = $(element).data("error");
    if (placement) {
      $(placement).append(error);
    } else {
      error.insertAfter(element);
    }
  },
  submitHandler: function () {
    var api = $("#api").val();

    if (api.trim() == "") {
      swal({
        title: "Advertencia",
        text: "Debes introducir y guardar tu ApiKey para poder enviar mensajes",
        icon: "warning",
      });
      return;
    }

    $.ajax({
      url: "aux",
      data: {
        cmd: "2fa_validate",
        dest: $("#destination_ve").val(),
        code: $("#verification").val(),
        formato: $("input[name=group3]:checked").val(),
      },
      method: "post",
      success: (res) => {
        $("#valform").get()[0].reset();
        swal({
          title: "Mensaje",
          text: res,
          icon: "info",
        });
      },
    });
  },
});
