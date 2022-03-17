<template>
    <base-section
      id="bio"
      class="bio-class"
      space="40"
    >
        <v-container>
            <h1>Meet the Team</h1>
            <br>
            <br>
            <div class="biocontainer">
            <div class="image">
              <img
                src='/jonathan.png'
                width="300"
                height="380"
                alt="Jonathan"
              />
            </div>
            <div class="text">
            <h2>Jonathan Shah</h2>
            <h3>Co-Founder – Chief Executive Officer</h3><br>
            <p>Born in Manhattan, Jonathan Shah was immersed in the worlds of financial markets and equities trading at a young age. He continued to develop his passion and interests while attending the esteemed Pine Crest School in South Florida. It was there that he also began exploring alternate asset classes that are not easily traded, such as real estate. He played influential roles in developing models which revolutionized day trading by reading the market and delivering forecasts in real time on any publicly traded stock or crypto-currency.</p>
            <!-- <p>He played influential roles in developing models which revolutionized day trading by reading the market and delivering forecasts in real time on any publicly traded stock or crypto-currency.</p> -->
            <p>Subsequently, Jonathan enrolled in Babson College&rsquo;s Entrepreneurship program, the world&rsquo;s #1 college for entrepreneurship, which focuses intensely on educating entrepreneurial leaders. He soon furthered his passion for the real estate industry after meeting Babson classmate Maxwell Schwarz. Together they founded Honely, LLC, a property technology company, which Jonathan personally funded through equity trading and investment. Now, Jonathan is the CEO of Honely.com, and is spearheading its mission to revolutionize the journey to home and real estate ownership by leveraging data science and predictive analytics algorithms to give average investors the tools they need to make fully informed real estate investment decisions at fair valuations.</p>
            </div>
            </div>
            <br>
            <div class="biocontainer">
            <div class="image">
              <img
                src='/FullSizeRender.jpg'
                width="300"
                height="400"
                alt="Maxwell"
              />
            </div>
            <div class="text">
            <h2>Maxwell Schwarz</h2>
            <h3>Co-Founder – President</h3><br>
            <p>Born in Plantation, Florida, Maxwell Schwarz has always been the hardest worker in the room, excelling in his studies and driving him towards many extracurricular feats including a state championship for hockey, mentoring and teaching special needs children, and  getting his real estate license at a young age.</p>
            <p>Following his enrollment in Babson College&rsquo;s Entrepreneurship program, the world&rsquo;s #1 college for entrepreneurship, he met classmate Jonathan Shah, and together co-founded Honely. His extensive knowledge and passion for real estate combined with Jonathan&rsquo;s skills allowed them to find pain points in the real estate space and solve them with unique solutions.</p>
            <p>Now, Maxwell is President of Honely.com, and is primarily focused on helping real estate investors and homeowners realize their home ownership goals. He also runs the company’s daily activities and coordinates all legal and financial matters.</p>
            </div>
            </div>
            <br>
            <div class="biocontainer">
            <div class="image">
              <img
                src='/21BF19CB-8A33-472C-87CC-F1E749EC6EF2.jpeg'
                width="300"
                height="380"
                alt="Jonathan"
              />
            </div>
            <div class="text">
            <h2>Jeff Lobb</h2>
            <h3>Advisor</h3><br>
            <p>Jeff Lobb is the Founder and CEO of SparkTank Media - A Speaking , Training and Coaching company that focuses on Sales, Marketing and Technology in the Real Estate, Mortgage and Title Industries.  Jeff Lobb is an International Speaker, Innovator and Consultant has been a REALTOR&reg; for over 26 years. He has more than 10 years experience in the Internet Technology  and Sales Management fields both with Fortune 500&reg; companies and startup ventures. Jeff&rsquo;s Sales, Data, and Wireless experience has held Management and Executive level positions with Companies such as IDT, LEVEL 3 , W2W, Bell Atlantic and Verizon Wireless.</p>
            <p>He is a Sales and Marketing Specialist coming from the perspective of a REALTOR! Over 26 years of Real Estate, he has been a top producing agent, has run Real Estate brokerages and has developed training and coaching products to serve the Real Estate Industry.</p>
            <p>Jeff has presented at Internet Conferences, Re/Max , Coldwell Banker, ERA, EXIT Realty, Inman Agent Reboot, Inman Connect, XPLODE Real Estate Technology Conferences, RIS Media, Retso, Midwest tech fair, WCR, and many Real Estate boards and MLS&rsquo;s . Jeff was the Co-Author of the Swanepoel Technology Report in 2013. Recently the Swanepoel Power 200 ranked Jeff in the TOP 20 most powerful in Social Media . Jeff has also recently been named Inman&rsquo;s Top 100 Most Influential Leaders in Real Estate for two consecutive years.</p>
            <p>He has a very accurate pulse on the industry and the many brokerage models. His diverse sales, marketing and technology background gives Jeff the vision, the passion and experience he refers to as &ldquo;the perfect storm&rdquo; - Growth Strategies for those who chose to execute the plan.</p>
            <p>Jeff is Fluent in the three languages....English, Real Estate, &amp; Technology</p>
            </div>
            </div>
        </v-container>
    </base-section>
</template>
<script>
    /* eslint-disable */
</script>
<style>
#bio {
  /* background-image: url('/dreamstime_l_136672848.jpg');
  background-size: cover; */
  background-color: #eeeeee;
}
.bio-class {
  background-size: contain;
  background-color: rgba(0, 0, 0, 0.2);
  color: #A0A0A0;
}
h1 {
    font-size: 45px;
}
h2 {
    text-align: left;
    font-size: 33px;
}
h3 {
    text-align: left;
    font-size: 20px;
}
p {
   font-size: 15px; 
}
.biocontainer {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  /* justify-content: center */
}
.biocontainer:first-of-type {
  margin-bottom: 5rem;
}
.biocontainer p {
  font-size: 17px;
}
.image {
  width: 25%;
}
.text {
  width: 70%;
}
img {
  width: 100%;
  height: auto;
  max-width: 300px;
  display: block;
  margin: 0 auto;
}
/* MOBILE */
@media only screen and (max-width: 1900px) {
  .image {
    width: 20%;
  }
  .text {
    width: 75%;
    margin-left: 3rem;
  }
}
@media only screen and (max-width: 1250px) {
  .image {
    width: 25%;
  }
  .text {
    width: 69%;
    margin-left: 3rem;
  }
}
@media only screen and (max-width: 850px) {
  .biocontainer:first-of-type {
    margin-bottom: 3rem;
  }
  .image {
    width: 100%;
  }
  .text {
    width: 100%;
    margin-left: 0;
  }
  h1 {
    text-align: center;
  }
  .text h2{
    text-align: center;
    margin-top: 1rem;
  }
  .text h3 {
    text-align: center;
  }
}
@media only screen and (max-width: 700px) {
  h1 {
    font-size: 34px;
  }
}
@media only screen and (max-width: 600px) {
  #bio .bio-class h1 {
    padding-right: 0;
    border: 1px solid red;
  }
}
@media only screen and (max-width: 400px) {
  h1 {
    font-size: 32px;
  }
}
</style>