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
            loan_name: "",
            max_amount: 0,
            interest: 0,
            payments: [],
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

        addPayment() {
            parseInt(this.payment)
            this.payments.push(parseInt(this.payment))
            this.payment = ''
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

        CreateLoan() {
         axios.post('api/post/loans',
                {
                    'name': this.loan_name,
                    'payments': this.payments,
                    'maxAmount': parseInt(this.max_amount),
                    'interest': parseInt(this.interest)
                })
                .then(response => {
                    if (response.status == 201) { window.location.href = `/api/loans` }
                })
                .catch(function(error){
                                   
                })
            },
    },
    computed: {
    },

})
app.mount('#app')