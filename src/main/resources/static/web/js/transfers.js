const app = Vue.createApp({
    data(){
        return{
            data_general: [],
            client: [],
            accounts: [],
            firstName:"",
            lastName:"",
            date:"",
            id: new URLSearchParams(location.search).get('id'),
            destiny:"",
            account_origin:"",
            account_destiny:"",
            amount:"",
            description:"",
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
                })
        },
        modificarSaldo(saldo){
            return new Intl.NumberFormat("en-US", {style: "currency", currency:"USD"}).format(saldo);
        },
        logOut(){
            return axios.post('/api/logout')
            .then(response=> window.location.href = "/web/index.html")
        },
        datemodified(date){
            return new Date(date).toLocaleDateString('es-co', {year:"numeric", month:"short", day:"numeric"})
        },
        
        generate_transfer(){
            let amount=this.amount
            let account_destiny=this.account_destiny
            let account_origin=this.account_origin
            let description=this.description
            return axios.post('/api/transactions',`accountOrigin=${account_origin}&accountDestiny=${account_destiny}&amount=${amount}&description=${description}`)
            .then(response=> window.location.href = "/web/transfers.html?id='+account.id")
            .catch(function(error){
                if(error.response.data=="Missing Account Origin"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Account Origin field is missing'})
                }
                if(error.response.data=="Missing Account Destiny"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Account Destiny field is missing'})
                }
                if(error.response.data=="Missing Amount"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Amount field is missing'})
                }
                if(error.response.data=="Missing Description"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Description field is missing'})
                }                
                if(error.response.data=="Account destiny and origin are the same"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Origin and destiny accounts are the same'})
                }
                if(error.response.data=="Destiny account not found"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Destiny account not found'})
                }
                if(error.response.data=="Origin account not found"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Origin account not found'})
                }
                if(error.response.data=="Origin account is not allowed"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Origin account is not allowed'})
                }
                if(error.response.data=="Your balance is not enough"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Your balance is not enough'})
                }
                if(error.response.data=="Exceed the limit per transaction"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Exceed the limit per transaction'})
                }
                if(error.response.data=="Transaction allowed"){
                    return Swal.fire({confirmButtonColor: '#2691d9',title:'Transaction allowed'})
                }                
            })
        },      
    },
    computed:{        
    },

})
app.mount('#app')