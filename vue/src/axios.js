import axios from "axios";
import router from "./router";
import Element from "element-ui";


//請求前後攔截器

// 設定反向代理，前端請求預設傳送到 http://localhost:8088/
axios.defaults.baseURL = 'http://localhost:8088/'

const request=axios.create({
    // timeout:50000,
    headers:{
        'Content-Type':'application/json ; charset=utf-8'
    }
})

request.interceptors.request.use(config =>{
   config.headers['Authorization']=localStorage.getItem("token")
   return config
})

request.interceptors.response.use(response => {
    let res=response.data
   
    if(res.code === 200){
      
        return response
    }else{
       
        Element.Message.error(!res.message?'系統異常':res.message)
        return Promise.reject(res.message)
    }    
},error => {
    
    if(error.response){
        if (error.response.data) {
            error.message=error.response.data;
        }
        
        if(error.response.status === 401 ){
            
            console.log("error.message : " + error.message)
            error.message="請登入";
            //Element.Message.error(error.message,{duration:3000})
            router.push('login')
        }
    }

    Element.Message.error(error.message,{duration:3000})
    return Promise.reject(error.message)
 }

)
export default request
