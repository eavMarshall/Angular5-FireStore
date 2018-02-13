class DialogState {
  title: string
  message: string
  callBack: Function
  cancelCallBack: Function
  acceptBtnTxt: string
  cancelBtnTxt: string

  constructor(title: string = '', message: string = '',
    callBack: Function = () => { }, cancelCallBack: Function = () => { },
    acceptBtnTxt: string = 'Ok', cancelBtnTxt: string = 'Cancel') {
    this.title = title
    this.message = message
    this.callBack = callBack
    this.cancelCallBack = cancelCallBack
    this.acceptBtnTxt = acceptBtnTxt
    this.cancelBtnTxt = cancelBtnTxt
  }
}

export default DialogState