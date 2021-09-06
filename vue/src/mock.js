const Mock=require('mockjs')

const Random=Mock.Random

let Result={
    code:200,
    msg:'操作成功',
    data:null
}

Mock.mock("/captcha",'get',()=>{
    Result.data={
        token:Random.string(32),
        captchImg:Random.dataImage('120x40','p7n5w')
    }
    return Result
})

Mock.mock("/login",'post',()=>{
    Result.code=200
    //Result.msg='驗證碼錯誤'

    return Result
})

Mock.mock("/sys/userInfo",'get',()=>{
    Result.data={
        id:1,
        username:'test',
        avatar:""

    }
    return Result
})

Mock.mock("/logout",'post',()=>{
 
    return Result
})

Mock.mock("/sys/getall",'get',()=>{
    let menu=[
        {
            'updatetime':'2021/05/09',
            'username':'xxxx',
            'account':'swad963000',
            'address':'123456',
            'tel':'0906555123',
            'status':'0'
        },  {
            'updatetime':'2021/06/09',
            'username':'yyy',
            'account':'gggg',
            'address':'123456789',
            'tel':'2252322',
            'status':'1'
        }
    ]
    Result.data=menu

   
    return Result
})

Mock.mock("/sys/getbyid",'get',()=>{
  
    Result.data=
    {
        'updatetime':'2021/05/09',
        'username':'xxxx',
        'account':'swad963000',
        'address':'123456',
        'tel':'0906555123',
        'status':'0'
    }

    
    return Result
})

