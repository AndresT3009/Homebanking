const app = Vue.createApp({
    data(){
        return{
            data_general: [],
            client: [],
            accounts: [],
            firstName:"",
            lastName:"",
            username_login:"",
            password_login:""
        }
    },

    created() {
        this.loadData();
    },
    
    methods: {
        loadData() {
            axios.get("/api/clients/")
                .then(response => {
                    this.data_general = response
                    this.client = this.data_general.data
                    this.accounts = this.client.accounts
                })
                .catch(err=>console.error(err.message));
        },
        login(){
            let username_login = this.username_login;
            let password_login = this.password_login;
            return axios.post('/api/login',`email=${username_login}&password=${password_login}`)
            .then(response=> window.location.href = "/web/account.html")
            .catch(function (error){
                Swal.fire({confirmButtonColor: '#2691d9',title:'Wrong password or username'})
            })
        },
    },
    
    computed:{        
    },

})
app.mount('#app')