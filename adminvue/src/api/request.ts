import axios from 'axios'

axios.defaults.timeout = 5000 // 超时时间设置
axios.defaults.withCredentials = true // true允许跨域

// Content-Type 响应头
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'
axios.defaults.headers.post['accept'] = '*/*'

//封装post
export function post(url, data = {}) {
    return new Promise((resolve, reject) => {
      axios.post(url, data).then(
        response => resolve(response.data),
        error => reject(error)
      );
    });
  }

//封装get方法(1)
export function getObjects(url){
    return new Promise((resolve,reject)=>{
        axios.get(url).then(
            response => resolve(response.data),
            error => reject(error) 
        )
    })
}
