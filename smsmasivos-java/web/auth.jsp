<%-- 
    Document   : auth
    Created on : 11-abr-2019, 9:30:08
    Author     : vzert13
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />
        <!--Import materialize.css-->
        <link rel="stylesheet" type="text/css" href="css/materialize.min.css" />

        <link rel="stylesheet" type="text/css" href="css/custom.css" media="screen,projection"/>

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  </head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Uso de API Envío SMSmasivos</title>
    </head>
    <body>
        <header class="topbar is_stuck" style="position: fixed; top: 0px; width: 1886px;">
            <nav class="navbar top-navbar navbar-expand-md navbar-light">
                <div class="navbar-header">
                    <a class="navbar-brand" href="main">
                        <span style="">
                            <img src="https://cdn-smsmasivos.sfo2.digitaloceanspaces.com/app/logo-users/sms-01.svg" alt="SMSMasivos" class="light-logo" width="165" height="50">
                        </span>
                        <b>
                            <img src="https://cdn-smsmasivos.sfo2.digitaloceanspaces.com/app/logo-users/sms-02.svg" alt="SMSMasivos" class="light-logo" width="50" height="50">
                        </b>
                    </a>
                </div>
            </nav>
        </header>
        
        <div class="container">
            <div class="row">
                <div class="col s5 m5">
                  <h4 style="color:#14477e;">1. Agrega tu API KEY</h4>
                </div>

                <div class="col s7 m7">
                  <h4 style="color:#14477e;">
                    2. Autenticaci&oacute;n de doble factor
                  </h4>
                </div>
            </div>

        <div class="row">
          <div class="col s5 m5">
            <div class="card">
              <div class="card-content">
                <div class="row">
                  <form class="col s12 m12" id="tokenform" method="post">
                    <div class="input-field col s12 m12">
                      <i class="material-icons prefix ">vpn_key</i>
                      <input id="api" type="text" name="api" />
                      <label for="api">API KEY</label>
                    </div>
                  </form>

                  <div class="col s12 m12 center">
                    <button
                      class="btn waves-effect waves-light blue darken-1"
                      type="submit"
                      name="action"
                      form="tokenform"
                    >
                      Guardar
                      <i class="material-icons right">save</i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col s7 m7">
            <div class="card">
              <div class="card-content">
                <div class="row">
                  <ul id="tabs-swipe-demo" class="tabs">
                    <li class="tab col s6">
                      <a class="active" href="#reg-tab">Registro</a>
                    </li>
                    <li class="tab col s6">
                      <a href="#validation-tab">Validaci&oacute;n</a>
                    </li>
                  </ul>
                  <div id="reg-tab" class="col s12">
                    <br>
                    <form
                      class="col s12 m12"
                      id="regform"
                    >
                      <div class="input-field col s12 m12">
                        <i class="material-icons prefix ">list</i>
                        <input id="nombre" type="text" name="api" />
                        <label for="nombre">Nombre de la compa&ntilde;ia</label>
                      </div>

                      <div class="input-field col s4 m4">
                        <i class="material-icons prefix ">phone</i>
                        <select id="country">
                          <option value="52" data-icon="img/mexico.svg" selected>52</option>
                          <option value="1" data-icon="img/estados-unidos.svg">1</option>
                          <option value="18" data-icon="img/republica-dominicana.svg">18</option>
                          <option value="34" data-icon="img/espana.svg">34</option>
                          <option value="51" data-icon="img/peru.svg">51</option>
                          <option value="53" data-icon="img/cuba.svg">53</option>
                          <option value="54" data-icon="img/argentina.svg">54</option>
                          <option value="55" data-icon="img/brasil.svg">55</option>
                          <option value="56" data-icon="img/chile.svg">56</option>
                          <option value="57" data-icon="img/colombia.svg">57</option>
                          <option value="58" data-icon="img/venezuela.svg">58</option>
                          <option value="502" data-icon="img/guatemala.svg">502</option>
                          <option value="503" data-icon="img/el-salvador.svg">503</option>
                          <option value="504" data-icon="img/honduras.svg">504</option>
                          <option value="505" data-icon="img/nicaragua.svg">505</option>
                          <option value="506" data-icon="img/costa-rica.svg">506</option>
                          <option value="507" data-icon="img/panama.svg">507</option>
                          <option value="591" data-icon="img/bolivia.svg">591</option>
                          <option value="593" data-icon="img/ecuador.svg">593</option>
                          <option value="598" data-icon="img/uruguay.svg">598</option>
                        </select>
                      </div>
                      <div class="input-field col s8 m8">
                        <input id="destination" type="tel"
                        name="destination" />
                        <label for="destination">Destinatario</label>
                      </div>

                      <div class="input-field col s12 m12">
                          <h6>Longitud de c&oacute;digo</h6>
                        <p>
                          <label>
                            <input name="group1" type="radio" value="4" checked />
                            <span>4</span>
                          </label>
                        </p>
                        <p>
                          <label>
                            <input name="group1" type="radio" value="5" />
                            <span>5</span>
                          </label>
                        </p>                      
                      </div>

                      <div class="input-field col s12 m12">
                          <h6>Formato</h6>
                        <p>
                          <label>
                            <input name="group2" type="radio" value="json" checked />
                            <span>JSON</span>
                          </label>
                        </p>
                        <p>
                          <label>
                            <input name="group2" type="radio" value="xml"/>
                            <span>XML</span>
                          </label>
                        </p>                      
                      </div>
                    </form>
                    <div class="col s12 m12 center">
                        <button
                          class="btn waves-effect waves-light blue darken-1"
                          type="submit"
                          name="action"
                          form="regform"
                        >
                          Enviar
                          <i class="material-icons right">send</i>
                        </button>
                      </div>
                  </div>



                  <div id="validation-tab" class="col s12">
                    <br>
                    <form
                      class="col s12 m12"
                      id="valform"
                    >
                      <div class="input-field col s12 m12">
                          <i class="material-icons prefix ">phone</i>
                        <input id="destination_ve" type="tel"
                        name="destination_ve" />
                        <label for="destination">Destinatario</label>
                      </div>

                      <div class="input-field col s12 m12">
                          <i class="material-icons prefix ">edit</i>
                        <input id="verification" type="text"
                        name="verification" />
                        <label for="verification">C&oacute;digo de verificaci&oacute;n</label>
                      </div>

                      <div class="input-field col s12 m12">
                          <h6>Formato</h6>
                        <p>
                          <label>
                            <input name="group3" type="radio" value="json" checked />
                            <span>JSON</span>
                          </label>
                        </p>
                        <p>
                          <label>
                            <input name="group3" type="radio" value="xml"/>
                            <span>XML</span>
                          </label>
                        </p>                      
                      </div>
                    </form>
                    <div class="col s12 m12 center">
                        <button
                          class="btn waves-effect waves-light blue darken-1"
                          type="submit"
                          name="action"
                          form="valform"
                        >
                          Validar
                          <i class="material-icons right">check</i>
                        </button>
                      </div>
                  </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
      </div>

      <div class="row w">
        <div class="col s6 m6 offset-s3 offset-m3">
          <h6 class="valign-wrapper center-align">
            <i class="material-icons">warning</i>&nbsp;&nbsp; Por favor,
            recuerda cambiar tu api key
          </h6>
        </div>
      </div>
 
    <script type="text/javascript" src="js/jquery.3.3.1.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script type="text/javascript" src="js/jquery.validate.js"></script>
    <script type="text/javascript" src="js/custom.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    </body>
</html>
