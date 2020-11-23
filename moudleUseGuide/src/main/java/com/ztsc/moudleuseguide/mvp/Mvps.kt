package com.ztsc.house.mvp


interface IPresenter<out View : IMvpView<IPresenter<View>>> :ILiveCycle{
    val view: View
}

interface IMvpView<out Presenter:IPresenter<IMvpView<Presenter>>>:ILiveCycle{
    val presenter: Presenter
}