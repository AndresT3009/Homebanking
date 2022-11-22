const app = Vue.createApp({
    data(){
        return{
            url:"http://localhost:8080/api/clients",
            data_general: [],
            client: [],
            accounts: [],
            name:"",
            lastName:"",
            email:"",
            password:"",
            accountType:"",
        }
    },

    created() {
        this.loadData();
    },
    
    methods: {
        loadData() {
            axios.get("/api/clients/")
                .then(response => {
                })
                .catch(err=>console.error(err.message));
        },
        SignUp(){
            let name = this.name;
            let lastname = this.lastname;
            let email = this.email.toLowerCase();
            let password = this.password;
            let accountType=this.accountType
            return axios.post('/api/clients',`firstName=${name}&lastName=${lastname}&email=${email}&password=${password}&accountType=${accountType}`)
            .then(response =>axios.post('/api/login',`email=${email}&password=${password}`)
            .then(response=> window.location.href = "/web/account.html"))
            .catch(function(error){
                if(error.response.data=="Missing First Name"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'First Name field is missing'})
                }
                if(error.response.data=="Missing Last Name"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Last Name field is missing'})
                }
                if(error.response.data=="Missing Email"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Email field is missing'})
                }
                if(error.response.data=="Missing Password"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Password field is missing'})
                }
                if(error.response.data=="Missing Account Type"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Account type field is missing'})
                }
                if(error.response.data=="Name already in use"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Username is already in use'})
                }
            })
        },
    },
    computed:{        
    },
})
app.mount('#app')