import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'UIX';

  cambiar_login() {
    // @ts-ignore
    document.querySelector('.cont_forms').className = "cont_forms cont_forms_active_login";
    // @ts-ignore
    document.querySelector('.cont_form_login').style.display = "block";
    // @ts-ignore
    document.querySelector('.cont_form_sign_up').style.opacity = "0";
    // @ts-ignore
    setTimeout(function(){  document.querySelector('.cont_form_login').style.opacity = "1"; },400);

    setTimeout(function(){
      // @ts-ignore
      document.querySelector('.cont_form_sign_up').style.display = "none";
    },200);
  }

  cambiar_sign_up() {
    // @ts-ignore
    document.querySelector('.cont_forms').className = "cont_forms cont_forms_active_sign_up";
    // @ts-ignore
    document.querySelector('.cont_form_sign_up').style.display = "block";
    // @ts-ignore
    document.querySelector('.cont_form_login').style.opacity = "0";
    // @ts-ignore
    setTimeout(function(){  document.querySelector('.cont_form_sign_up').style.opacity = "1";
    },100);
    // @ts-ignore
    setTimeout(function(){   document.querySelector('.cont_form_login').style.display = "none";
    },400);
  }

  ocultar_login_sign_up() {
// @ts-ignore
    document.querySelector('.cont_forms').className = "cont_forms";
    // @ts-ignore
    document.querySelector('.cont_form_sign_up').style.opacity = "0";
    // @ts-ignore
    document.querySelector('.cont_form_login').style.opacity = "0";

    setTimeout(function(){
      // @ts-ignore
      document.querySelector('.cont_form_sign_up').style.display = "none";
      // @ts-ignore
      document.querySelector('.cont_form_login').style.display = "none";
    },500);
  }
}
