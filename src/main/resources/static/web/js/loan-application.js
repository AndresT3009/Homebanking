const app = Vue.createApp({
    data() {
        return {
            data_general: [],
            client: [],
            accounts: [],
            date: "",
            id: new URLSearchParams(location.search).get('id'),
            destiny: "",
            account_destiny: "",
            description: "",
            loan: [],
            loans_data: [],
            maxAmount: [],
            loanTypeId: "",
            amount: "",
            payments: [],
            payment: "",
            accountNumber: "",
        }
    },

    created() {
        this.loadData();
        axios.get("/api/loans")
            .then(response => {
                this.loans_data = response.data
            })
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

        modificarSaldo(saldo) {
            return new Intl.NumberFormat("en-US", { style: "currency", currency: "USD" }).format(saldo);
        },


        logOut() {
            return axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
        },


        datemodified(date) {
            return new Date(date).toLocaleDateString('es-co', { year: "numeric", month: "short", day: "numeric" })
        },

        Loan() {
            axios.post('/api/loans',
                {
                    'id': this.loanTypeId,
                    'payments': this.payments,
                    'amount': this.amount,
                    'accountnumber': this.accountNumber
                })
                .then(response => {
                    if (response.status == 201) { window.location.href = `/web/account.html` }
                })
                .catch(function(error){
                    if(error.response.data=="Account Number does not exist"){
                        return Swal.fire({confirmButtonColor: '#2691d9',title:'Account Number does not exist'})
                    }
                    if(error.response.data=="Credit type does not exist"){
                        return Swal.fire({confirmButtonColor: '#2691d9',title:'Credit type does not exist'})
                    }
                    if(error.response.data=="Unable to lend the requested amount"){
                        return Swal.fire({confirmButtonColor: '#2691d9',title:'Unable to lend the requested amount'})
                    }
                    if(error.response.data=="The amount of installments requested is not among those established for this credit"){
                        return Swal.fire({confirmButtonColor: '#2691d9',title:'The amount of installments requested is not among those established for this credit'})
                    }                
                    if(error.response.data=="Please select credit type"){
                        return Swal.fire({confirmButtonColor: '#2691d9',title:'Please select credit type'})
                    }
                    if(error.response.data=="Please select account destiny"){
                        return Swal.fire({confirmButtonColor: '#2691d9',title:'Please select account destiny'})
                    }
                    if(error.response.data=="Please select amount"){
                        return Swal.fire({confirmButtonColor: '#2691d9',title:'Please select amount'})
                    }
                    if(error.response.data=="You already have a mortgage loan"){
                        return Swal.fire({confirmButtonColor: '#2691d9',title:'You already have a mortgage loan'})
                    }
                    if(error.response.data=="You already have a personal loan"){
                        return Swal.fire({confirmButtonColor: '#2691d9',title:'You already have a personal loan'})
                    }
                    if(error.response.data=="You already have a vehicle loan"){
                        return Swal.fire({confirmButtonColor: '#2691d9',title:'You already have a vehicle loan'})
                    }
                    if(error.response.data=="Transaction not allowed"){
                        return Swal.fire({confirmButtonColor: '#2691d9',title:'Transaction not allowed'})
                    }                
                })
            },
    },
    computed: {
    },

})
app.mount('#app')