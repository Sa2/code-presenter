package controllers

import javax.inject.Inject

import play.api.mvc.{Action, Controller}

/**
  * Created by Sa2 on 2016/08/21.
  */
class SessionController @Inject() extends Controller{

  def presenter = Action {
    Ok(views.html.session.presenter())
  }


  def audience = TODO
}
