import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
   
   
    state: {
   
   
        token:''
       
    },
    mutations: {
   
   
        // set
        SET_TOKEN: (state, token) => {

            state.token = token
            localStorage.setItem("token", token)
        },
        restState : (state) => {
            state.token = ''
        }
            
    },
    getters: {
   
   

    },
    actions: {
   
   
    },
    modules: {
   
   
    }
})