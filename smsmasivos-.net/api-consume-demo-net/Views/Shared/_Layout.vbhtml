<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>API CONSUME</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet" />
    <!--Import materialize.css-->
    <link type="text/css"
          rel="stylesheet"
          href="~/Content/materialize.min.css"
          media="screen,projection" />
    <link type="text/css"
          rel="stylesheet"
          href="~/Content/Site.css"
          media="screen,projection" />
</head>
<body>
    <div class="container body-content">
        @RenderBody()
        <hr />
        <footer>
            <p>&copy; @DateTime.Now.Year - Mi aplicación ASP.NET</p>
        </footer>
    </div>

    @Scripts.Render("~/bundles/jquery")

    @RenderSection("scripts", required:=False)
    <script type="text/javascript"
            src="~/Scripts/materialize.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script type="text/javascript"
            src="~/Scripts/jquery.validate.min.js"></script>

    <script>

        $(document).ready(function() {
            $("textarea#textarea1").characterCounter();
            $("select").formSelect();
            $(".tabs").tabs();

        });

        $(document).on("click", "li a", function() {
            if ($(this).hasClass("active")) {
                $("#regform")
                .get()[0]
                .reset();
            $("#valform")
                .get()[0]
                .reset();
            }
        });

        //credits
        $("#request_credits").on("click", e => {
            $(e.currentTarget).addClass("disabled");
            var apikey = $("#api").val();

            if ( apikey.trim() == "") {
            swal({
            title: "Advertencia",
            text:
                "Debes introducir tu ApiKey para poder enviar mensajes",
            icon: "warning"
            });
                $("#request_credits").removeClass("disabled");
            return;
            }


            $.ajax({
                url: "@Url.Action("Credits", "sms")",
                data: {
                    action_cmd: "credits",
                    apikey: apikey
                },
                dataType: "json",
                type: "POST",
                success: res => {

                    $("#request_credits").removeClass("disabled");

                    if (res.success == true) {
                        $("#apiform")
                            .get()[0]
                            .reset();
                        swal({
                            title: "Mensaje",
                            text: "Cr\u00E9ditos disponibles:  " + res.credit,
                            icon: "info"
                        });
                    } else {
                        $("#apiform")
                            .get()[0]
                            .reset();
                        swal({
                            title: "Información no Disponible",
                            text: res.message,
                            icon: "warning"
                        });
                    }
                }
            });
        });

        //send
        $("#apiform").validate({
            rules: {
            destination: {
                required: true,
                minlength: 10
            },
            content: {
                required: true,
                maxlength: 160
            }
            },
            messages: {
            destination: {
                required: "Requerido",
                minlength: "M\u00EDnimo 10 d\u00EDgitos"
            },
            content: {
                required: "Requerido",
                maxlength: "No puede exceder 160 caracteres"
            }
            },
            errorElement: "span",
            errorPlacement: function(error, element) {
            var placement = $(element).data("error");
            if (placement) {
                $(placement).append(error);
            } else {
                error.insertAfter(element);
            }
            },
            submitHandler: function() {
            var apikey = $("#api").val();
            var nombre = $("#nombre").val();
            var sandbox = "";

            if ($("#sandbox:checked").length) {
                sandbox = 1;
            } else {
                sandbox = 0;
            }

            if (apikey.trim() == "") {
                swal({
                title: "Advertencia",
                text:
                    "Debes introducir tu ApiKey para poder enviar mensajes",
                icon: "warning"
                });
                return;
            }

            let this_c = $("#country option:selected").text();
            let this_dest = $("#destination").val();
            let this_texto = $("#textarea1").val();


            $.ajax({
                url: "@Url.Action("Send", "sms")",
                data: {
                    apikey: apikey,
                    country: this_c,
                    dest: this_dest,
                    texto: this_texto,
                    sand: sandbox,
                    nombre: nombre
                },
                dataType: "json",
                type: "POST",
                success: res => {

                    $("#apiform")
                        .get()[0]
                        .reset();

                    swal({
                        title: "Mensaje",
                        text: res.message,
                        icon: "info"
                    });
                }

            });
            }
        });

        //2faregister
        $("#regform").validate({
            rules: {
            destination: {
                required: true,
                minlength: 10
            }
            },
            messages: {
            destination: {
                required: "Requerido",
                minlength: "M\u00EDnimo 10 d\u00EDgitos"
            }
            },
            errorElement: "span",
            errorPlacement: function(error, element) {
            var placement = $(element).data("error");
            if (placement) {
                $(placement).append(error);
            } else {
                error.insertAfter(element);
            }
            },
            submitHandler: function() {
            var apikey = $("#api").val();
            var nombre = $("#nombre").val();
            var digitos = $("input[name=group1]:checked").val();
            var formato = $("input[name=group2]:checked").val();
            var this_c = $("#country option:selected").text();
            var this_dest = $("#destination").val();

            if (apikey.trim() == "") {
                swal({
                title: "Advertencia",
                text:
                    "Debes introducir tu ApiKey para poder enviar mensajes",
                icon: "warning"
                });
                return;
            }

            $.ajax({
                url: "@Url.Action("Register", "sms")",
                data: {
                    action_cmd: "2fa_register",
                    apikey: apikey,
                    nombre: nombre,
                    country: this_c,
                    dest: this_dest,
                    digitos: digitos,
                    formato: formato
                },
                method: "post",
                dataType: "json",
                success: res => {

                $("#regform")
                    .get()[0]
                    .reset();
                swal({
                    title: "Mensaje",
                    text: res.message,
                    icon: "info"
                });
                }
            });
            }
        });

        //2faverification
        $("#valform").validate({
          rules: {
            destination_ve: {
              required: true,
              minlength: 10
            },
            verification: {
              required: true,
              minlength: 4
            }
          },
          messages: {
            destination_ve: {
              required: "Requerido",
              minlength: "M\u00EDnimo 10 d\u00EDgitos"
            },
            verification: {
              required: "Requerido",
              maxlength: "Deben ser por lo menos 4 caracteres"
            }
          },
          errorElement: "span",
          errorPlacement: function(error, element) {
            var placement = $(element).data("error");
            if (placement) {
              $(placement).append(error);
            } else {
              error.insertAfter(element);
            }
          },
            submitHandler: function () {

            var apikey = $("#api_val").val();
            var formato = $("input[name=group3]:checked").val();
            var this_dest = $("#destination_ve").val();

            var code = $("#verification").val();

            if (apikey.trim() == "") {
              swal({
                title: "Advertencia",
                text:
                  "Debes introducir tu ApiKey para poder enviar mensajes",
                icon: "warning"
              });
              return;
            }

            $.ajax({
              url: "@Url.Action("Validate", "sms")",
                data: {
                    action_cmd: "2fa_validate",
                    apikey: apikey,
                    code: code,
                    dest: this_dest,
                    formato: formato
                },
                method: "post",
                dataType: "json",
                success: res => {
                $("#valform")
                  .get()[0]
                  .reset();
                swal({
                    title: "Mensaje",
                    text: res.message,
                  icon: "info"
                });
              }
            });
          }
        });

    </script>
</body>
</html>
