import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export const store = new Vuex.Store({
    //strict: true,
    state: {
        userID:'',
        userName: '',
        role:""
    },
    getters: {
        
    },
    mutations: {
      initialiseStore(state) {
        if (localStorage.getItem('timestamp')) {
            if(new Date().getTime() - localStorage.getItem('timestamp')  > 300000){
                state.userID = '';
                state.userName = '';
                state.role = '';
            }else{
                state.userID = localStorage.getItem('userID');
                state.userName = localStorage.getItem('userName');
                state.role = localStorage.getItem('role');
            }
        }
      },
      saveUserData:(state , data) =>{
        state.userID = data._id;
        state.userName = data._name;
        state.role = data._role;
        localStorage.setItem('userID', data._id);
        localStorage.setItem('userName', data._name);
        localStorage.setItem('role', data._role);
        localStorage.setItem('timestamp', new Date().getTime())
      },

    },
    actions: {
    },
    
});

/*
const getDefaultState = () => {
    return {
    }
};
function parseJSON(resp) {
    return resp.json();
}
function parseText(resp) {
    return resp.text();
}
function checkStatus(resp) {
    console.log('status');
    if (resp.status >= 200 && resp.status < 300) {
        console.log('good status');
        return resp;
    }
    console.log('bad status');
    return this.parseJSON(resp).then((resp) => {
        throw resp;
    });
}
*/