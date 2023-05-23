import { post, getObjects } from './request'
const HttpManager = {
    //===========================admin=================================
    // 是否登录成功
    getLoginStatus: (params) => post(`http://localhost:8080/admin/login/status`, params),
    //===========================item=================================
    //请求Item分页数据
    getPagesItems:(size,page)=>getObjects(`http://localhost:8080/admin/item/show?page=${page}&size=${size}`),
    //===========================person=================================
    //请求Person分页数据
    getPagesPeople:(size,page)=>getObjects(`http://localhost:8080/admin/person/show?page=${page}&size=${size}`),
    //===========================Departments=================================
    //请求Department分页数据
    getPagesDepartments:(size,page)=>getObjects(`http://localhost:8080/admin/department/show?page=${page}&size=${size}`)
}
export { HttpManager }