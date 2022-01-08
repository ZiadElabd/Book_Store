import Vue from 'vue'

Vue.mixin({
    methods: {
        checkStatus: function (resp) {
            // console.log('status');
            // console.log(resp);
            // console.log(resp.status);
            if (resp.status >= 200 && resp.status < 300) {
                console.log('good status');
                return true;
            }
            console.log('bad status');
            return false;
        },
        parseJSON: function (resp) {
            //console.log(resp);
            //console.log(resp.text());
            return resp.text() ? resp.text() : resp;
        },
    }
})