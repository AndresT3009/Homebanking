const app = Vue.createApp({
    data(){
        return{
            data_general: [],
            client: [],
            accounts: [],
            loans:[],
            firstName:"",
            lastName:"",
            detalle_cuentas:[],
            date:"",
            account_type:"",
            current_account:"",
            savings_account:""
        }
    },

    created() {
        this.loadData();
    },
    
    methods: {
        loadData() {
            axios.get("/api/clients/current")
                .then(response => {
                    this.data_general = response
                    this.client = this.data_general.data
                    this.accounts = this.client.accounts
                    this.loans = this.client.clientLoans
                })
        },

        modificarSaldo(saldo){
            return new Intl.NumberFormat("en-US", {style: "currency", currency:"USD"}).format(saldo);
        },

        logOut(){
            return axios.post('/api/logout')
            .then(response=> window.location.href = "http://localhost:8080/web/index.html")
        },

        create_account(){
            let type=this.account_type
            return axios.post('/api/clients/current/account',`accountType=${type}`)
            .then(response=> window.location.href = `http://localhost:8080/web/account.html` )
            .catch(function(error){
                if(error.response.data=="Missing Account Type"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Account type field is missing'})
                }
            })    
        },

        datemodified(date){
            return new Date(date).toLocaleDateString('es-co', {year:"numeric", month:"short", day:"numeric"})
        },
    },
    computed:{        
    },

})
app.mount('#app')