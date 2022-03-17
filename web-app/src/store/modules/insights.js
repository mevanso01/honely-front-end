import axios from 'axios'

export const state = () => ({
    allTemp: [{
        id: 1,
        title: '5 Things to Consider When Purchasing Your First&nbsp;Home',
        subtitle: '<p>Don’t make rookie mistakes. Learn what to look for when you go house shopping.</p>',
        truncatedBody: 'Taking the first step onto the property ladder is one of the biggest moments in anyone’s life. Buying a home is more than a purchase, it’s a major investment, almost certainly the biggest at that point in the buyer’s life.',
        body: '<p>Taking the first step onto the property ladder is one of the biggest moments in anyone’s life. Buying a home is more than a purchase, it’s a major investment, almost certainly the biggest at that point in the buyer’s life.</p><p>Being aware of the pitfalls around every corner is key to being able to avoid them. Learning about the best practices for budgeting and financing options will allow you to put your best foot forward and make the right purchase for both now and your future.</p><h3>How much can you actually afford to pay?</h3><p>It’s recommended that home buyers keep their house payments to less than 25% of their income. This is not a hard and fast rule, exceptions will occur. For example, if you are already carrying student, or other forms of debt, it would be wiser to consider a lower percentage of income.</p><h3>What type of mortgage should you choose?</h3><p>It is crucial to understand which type of mortgage will best suit your needs. The biggest choice is between a fixed rate and an adjustable rate mortgage.</p><p>With a fixed rate mortgage, you have certainty of what your repayments will be. They will not vary, offering greater budgetary transparency. However, to mitigate risk for the lender, interest rates typically begin higher and if they fall, your payments won’t follow suit. Occasionally, there are also penalties for early repayment.</p><p>On the other hand, adjustable rate mortgages track a fixed economic indicator, most commonly the prevailing interest rate. This means that if interest rates fall, so do your repayments, but you are also exposed to rising costs after an interest rate hike.</p><p>The length of your mortgage is also a huge consideration. The most common mortgage length is 30 years, but borrowers can opt for shorter or longer loans. A 15-year loan will mean larger repayments, but less interest paid, while a 30-year mortgage means paying less each month but over a longer period, meaning more interest.</p><h3>Don’t forget the closing costs!</h3><p>Buying and owning a house comes with all sorts of added expenses. But most importantly, buyers must plan with their closing costs in mind. While many people rightly focus on getting their down payment in order to secure the mortgage, you will have to set some money aside for closing costs.</p><p>It’s wise to earmark 2-5% of the house price for costs including, but not limited to, appraisals, home inspections, mortgage-related fees, property taxes and insurance.</p><h3>Get pre-approved for a loan</h3><p>Considering sellers can often be relying on a quick sale to buy their next home, it’s crucial you appear as a serious buyer. Getting pre-qualified for a loan and receiving a preapproval letter from your lender is a great way of doing this.</p><p>This will involve providing salary and other financial information to your lender to prove your capability of repaying a loan.</p><h3>Will your home increase over time?</h3><p>Everyone wants their home to be a wise real estate investment. Paying a mortgage means you are spending more than the closing price of your house, so buying a property that will appreciate in value is important.</p><p>But how do you know whether your home will be worth more in the future? That’s where Honely will help.</p><p>Our one-of-a-kind, forward-looking algorithm leverages data science and machine learning capabilities to provide future home values. We give home buyers the tools they need to make informed decisions and insight into the results their purchase will yield in the long-term.</p><p>Use the power of data and our industry leading forecasting tools to give you the confidence to make a great real estate investment.</p>',
        post_date: '2021-05-09',
        image: 'blog-01.jpg',
        path: '/2021/05/09/5-things-to-consider',
    }, {
        id: 2,
        title: 'U.S. Housing Market Will Withstand a Wave of Foreclosures when Forbearance Ends',
        subtitle: 'This is the subtitle',
        truncatedBody: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        body: '<p>body</p>',
        post_date: '2021-01-27',
        image: 'temp-blog-02.jpg',
        path: '/2020/12/09/us-housing-market',
    }, {
        id: 3,
        title: 'Migrants Likely Helped Flip Georgia Blue, With 50,000 More People Moving Into the State Than Out Last Year',
        subtitle: 'This is the subtitle',
        truncatedBody: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        body: '<p>body</p>',
        post_date: '2021-01-03',
        image: 'temp-blog-03.jpg',
        path: '/2020/11/28/migrants-likely-helped',
    }, {
        id: 4,
        title: 'Condos are Selling for a Record 17% Discount to Single-Family Homes as the Coronavirus Fuels Demand for Space',
        subtitle: 'This is the subtitle',
        truncatedBody: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        body: '<p>body</p>',
        post_date: '2020-12-09',
        image: 'temp-blog-04.jpg',
        path: '/2020/11/14/condos-are-selling',
    }, {
        id: 5,
        title: 'Car-Dependent Neighborhoods Are Hotter THan Ever Mid-Pandemic, With Home Prices up 15% - But Walkable Neighborhoods Aren\'t Far Behind',
        subtitle: 'This is the subtitle',
        truncatedBody: 'Supply is down and competition is fierce in suburbs and rural areas. The housing market is also hot in walkable urban neighborhoods, with prices up 11%, as homebuyers display renewed interest in city life. The median home-sale price in car-dependent neighborhoods nationwide rose 14.9% year over year to $345,000 in October, the biggest increase yet.',
        body: '<p>body</p>',
        post_date: '2021-02-09',
        image: 'temp-blog-05.jpg',
        path: '/2020/12/09/car-dependent-neighborhoods',
    }],
    all: [],
    targetPosts: [],
    total: 0,
    footerPosts: [],
})

// getters
export const getters = {
    allPosts: state => {
        return state.all
    },
    targetPosts: state => {
        return state.targetPosts
    },
    totalPosts: state => {
        return state.total
    },
    allTempPosts: state => {
        return state.allTemp
    },
    footerPosts: state => {
        return state.footerPosts
    },
}

// actions
export const actions = {
    getPosts ({ commit }, payload) {
        axios
            .get('https://api.honely.com/lookup/blog_posts', {
                params: payload,
            })
            .then((response) => {
                const data = response.data

                // data.rows.forEach(function (item) {
                // data.forEach(function (item) {
                //     item.image_filename = item.image_filename.substring(1)
                //     if (item.image_filename) {
                //         item.image = require('@/assets/' + item.image_filename)
                //     } else {
                //         item.image = require('@/assets/blog-01.jpg')
                //     }
                // })

                commit('SET_ALL_POSTS', data)
                commit('SET_TOTAL_POSTS', data.length)
            })
    },
    getTargetPost ({ commit }, payload) {
        axios
            .get('https://api.honely.com/lookup/blog_posts_finder', {
                params: payload,
            })
            .then((response) => {
                const data = response.data

                // data.rows.forEach(function (item) {
                // data.forEach(function (item) {
                //     item.image_filename = item.image_filename.substring(1)
                //     if (item.image_filename) {
                //         item.image = require('@/assets/' + item.image_filename)
                //     } else {
                //         item.image = require('@/assets/blog-01.jpg')
                //     }
                // })

                commit('SET_TARGET_POST', data)
            })
    },
    getFooterPosts ({ commit }, payload) {
        axios
            .get('https://api.honely.com/lookup/blog_posts', {
                params: payload,
            })
            .then((response) => {
                const data = response.data

                // data.rows.forEach(function (item) {
                //     if (item.image_filename) {
                //         item.image = require('@/assets/' + item.image_filename)
                //     } else {
                //         item.image = require('@/assets/blog-01.jpg')
                //     }
                // })

                commit('SET_FOOTER_POSTS', data)
            })
    },
}

// mutations
export const mutations = {
    SET_ALL_POSTS (state, posts) {
        state.all = posts
    },
    SET_TARGET_POST (state, targetPosts) {
        state.targetPosts = targetPosts
    },
    SET_TOTAL_POSTS (state, total) {
        state.total = total
    },
    SET_FOOTER_POSTS (state, posts) {
        state.footerPosts = posts
    },
}
