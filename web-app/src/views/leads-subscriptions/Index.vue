<template>
  <!-- vx: must refactor this file asap -->
  <div
    v-if="agent_id !== null"
    class="leads-subscription-container"
  >
    <div
      v-if="loading"
      justify="center"
      align="center"
      class="flex-wrapper"
    >
      <div class="flex-col12">
        <div>
          <circle-spin :loading="true" />
        </div>
      </div>
    </div>
    <div
      v-if="!loading"
      class="flex-wrapper"
    >
      <div class="flex-col12">
        <h1>
          Buy Leads
        </h1>
        <v-tabs
          v-model="tab"
          class="leads-subs-tabs"
          centered
        >
          <!-- <v-tab v-for="tab of tabs" :key="tab.id">{{ tab.name }}</v-tab> -->
          <v-tab
            href="#tab-3"
            style="color: #ffffff"
            @click="fetchLeadsSlotsStatus()"
          >
            Leads
            <br v-if="$vuetify.breakpoint.smAndDown">
            subscription
          </v-tab>
          <v-tab
            href="#tab-1"
            style="color: #ffffff"
            @click="fetchButtonSubscriptionStatus()"
          >
            Button subscription
          </v-tab>
          <!-- vx: subscribing, cancellation, subscription details, lead generation url etc all go here.. -->
          <!-- <v-tab>Buy Leads</v-tab> -->
          <!-- <v-tab
          href="#tab-2"
          active-class="active-tab"
          style="color: white;"
          @click="fetchLeadsList()"
          >Leads List</v-tab> -->
          <v-tab-item
            value="tab-3"
          >
            <v-tabs
              class="leads-subtype-tabs"
              left
            >
              <v-tab
                v-if="isAgent"
                style="color: #24cb43"
                @click="resetFetchSlotCount(agent)"
              >
                AGENT/BROKER
              </v-tab>
              <v-tab
                v-if="isLender"
                style="color: #24cb43"
                @click="resetFetchSlotCount(lender)"
              >
                LENDER
              </v-tab>
              <v-tab
                v-if="isGeneralContractor"
                style="color: #24cb43"
                @click="resetFetchSlotCount(gc)"
              >
                GENERAL CONTRACTOR
              </v-tab>
              <v-tab-item v-if="isAgent">
                <div class="leads-subs-section top">
                  <h2 class="btn-subscription-details-heading">
                    Buy a share of the leads in your zip code(s)
                  </h2>
                  <div class="btn-subscription-details">
                    <!-- Search for slots availability by zity or zipcode: -->
                    <!-- <v-text-field
                v-model="leadSlotSearchInput"
                class="centered-input"
                outlined
                placeholder="Search for slots availability by city or zipcode:"
                style="width:50%; margin-left:25%; text-align: center;max-height: 65px"
                />
                <button
                class="btn-subscription-details-heading"
                @click="fetchZipSlotCount()"
                >
                  Search
                </button> -->
                    <div
                      v-if="freeSlotsRemainingA > 0"
                    >
                      <p>You have {{ freeSlotsRemainingA }} slot subscription(s) with free trial remaining.</p>
                    </div>
                    <div>
                      <search-suggestions-lead-slots
                        :user-type="agent"
                        @fetchSlotCount="fetchSlotCount"
                      />
                    </div>
                    <div
                      v-if="zipSlotRecordsA.length > 0 && $vuetify.breakpoint.mdAndUp"
                    >
                      <div
                        class="leads-subs-search-results flex-wrapper"
                      >
                        <div class="flex-col-md-4">
                          <div class="full">
                            <span class="heading-font">Zipcode</span>
                          </div>
                        </div>
                        <div class="flex-col-md-4">
                          <div class="full">
                            <span class="heading-font">Number Of Slots Available</span>
                          </div>
                        </div>
                        <div class="flex-col-md-4">
                          <div class="full">
                            <span class="heading-font">Price</span>
                          </div>
                        </div>
                      </div>
                      <div
                        v-if="zipSlotRecordsA.length > 0"
                      >
                        <div
                          v-for="item in zipSlotRecordsA"
                          :key="item.zipcode"
                          class="flex-wrapper"
                        >
                          <div class="flex-col-md-4 mt-8">
                            <span class="zip-font">{{ item.zipcode }}</span>
                          </div>
                          <div class="flex-col-md-4 mt-8">
                            <span>
                              <i
                                v-if="item.slotsAvailable < 3"
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                aria-hidden="true"
                              />
                              <i
                                v-else
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: #06A550; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                aria-hidden="true"
                              />
                            </span>
                            <span>
                              <i
                                v-if="item.slotsAvailable < 2"
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                aria-hidden="true"
                              />
                              <i
                                v-else
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: #06A550; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                aria-hidden="true"
                              />
                            </span>
                            <span>
                              <i
                                v-if="item.slotsAvailable < 1"
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                aria-hidden="true"
                              />
                              <i
                                v-else
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: #06A550; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                aria-hidden="true"
                              />
                            </span>
                          </div>
                          <div class="flex-col-md-4 mt-8">
                            <div v-if="item.slotsAvailable > 0 && !isInOwnedSlots(item.zipcode, agent)">
                              <div v-if="freeSlotsRemainingA === 0">
                                <span class="price-font">$20.00</span>
                                <v-btn
                                  v-if="!isZipInCart(item.zipcode, agent)"
                                  color="#24cb43"
                                  style="margin-left: 20px"
                                  @click="addZipToCart(item.zipcode, agent)"
                                >
                                  Subscribe Now
                                </v-btn>
                              </div>
                              <div v-if="freeSlotsRemainingA > 0">
                                <v-btn
                                  v-if="!isZipInCart(item.zipcode, agent)"
                                  color="#24CB43"
                                  @click="doSlotFreeTrialSubscription(item.zipcode, agent)"
                                >
                                  Subscribe For Free Trial
                                </v-btn>
                              </div>
                            </div>
                            <div v-else>
                              <div
                                v-if="isInOwnedSlots(item.zipcode, agent)"
                              >
                                <span>Subscribed!</span>
                              </div>
                              <div v-else>
                                No Slots Available!
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div
                      v-if="zipSlotRecordsA.length > 0 && $vuetify.breakpoint.smAndDown"
                    >
                      <div
                        v-for="item in zipSlotRecords"
                        :key="item.zipcode"
                        class="flex-wrapper"
                      >
                        <div class="flex-col-md-5">
                          <div class="full">
                            <span><b>Zipcode</b></span>
                          </div>
                          <div class="full">
                            <span><b>Slots</b></span>
                          </div>
                          <div class="full">
                            <span><b>Price</b></span>
                          </div>
                        </div>
                        <div class="flex-col-md-7">
                          <div class="full">
                            <span class="zip-font">{{ item.zipcode }}</span>
                          </div>
                          <div class="full">
                            <!-- {{ item.slotsAvailable }} -->
                            <span>
                              <i
                                v-if="item.slotsAvailable < 3"
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: grey;'"
                                aria-hidden="true"
                              />
                              <i
                                v-else
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: #06A550;'"
                                aria-hidden="true"
                              />
                            </span>
                            <span>
                              <i
                                v-if="item.slotsAvailable < 2"
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: grey;'"
                                aria-hidden="true"
                              />
                              <i
                                v-else
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: #06A550;'"
                                aria-hidden="true"
                              />
                            </span>
                            <span>
                              <i
                                v-if="item.slotsAvailable < 1"
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: grey;'"
                                aria-hidden="true"
                              />
                              <i
                                v-else
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: #06A550;'"
                                aria-hidden="true"
                              />
                            </span>
                          </div>
                          <div class="full">
                            <div v-if="item.slotsAvailable > 0 && !isInOwnedSlots(item.zipcode, agent)">
                              <div v-if="freeSlotsRemaining === 0">
                                <span class="zip-font">$20.00</span>
                                <v-btn
                                  v-if="!isZipInCart(item.zipcode, agent)"
                                  color="#24CB43"
                                  style="margin-left: 5px"
                                  @click="addZipToCart(item.zipcode, agent)"
                                >
                                  Subscribe Now
                                </v-btn>
                              </div>
                              <div v-if="freeSlotsRemaining > 0">
                                <v-btn
                                  v-if="!isZipInCart(item.zipcode, agent)"
                                  color="#24CB43"
                                  @click="doSlotFreeTrialSubscription(item.zipcode, agent)"
                                >
                                  Free Trial
                                </v-btn>
                              </div>
                            </div>
                            <div v-else>
                              <div
                                v-if="isInOwnedSlots(item.zipcode)"
                              >
                                <span>Subscribed!</span>
                              </div>
                              <div v-else>
                                No Slots Available!
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div
                    class="check-box-section mt-10"
                  >
                    <br>
                    <!-- <input
                      v-model="agreedLicensing"
                      type="checkbox"
                    > -->
                    <v-checkbox
                      v-model="agreedLicensing"
                      label=""
                      class="auth-modal-form-control"
                    />
                    <label>
                      By checking here you agree to Honely's Lead Licensing Agreement.
                    </label>
                  </div>
                </div>
                <br>
                <div class="leads-subs-section bottom">
                  <h2
                    class="btn-subscription-details-heading"
                  >
                    My Zip Codes
                  </h2>
                  <div class="btn-subscription-details">
                    <div
                      v-if="slotSubscriptionsLoaded && slotSubscriptionDetailsA.length === 0"
                    >
                      <p>
                        No Active Zip Codes
                      </p>
                    </div>
                    <!-- vx: check neten's object and coorect below part (zipcode etc) -->
                    <div
                      v-else
                      class="leads-subs-zipcodes"
                    >
                      <div
                        v-for="item in slotSubscriptionDetailsA"
                        :key="item.zipcode"
                        class="leads-subs-zip"
                      >
                        <div class="leads-subs-zip-info-container">
                          <p class="leads-subs-zip-title">
                            <i class="fas fa-map-marker-alt" /> {{ item.zipcode }} <span style="float: right;">
                              <v-tooltip
                                content-class="custom-tooltip"
                                bottom
                                max-width="300px"
                              >
                                <template #activator="{ on, attrs }">
                                  <i
                                    class="fas fa-info-circle"
                                    v-bind="attrs"
                                    v-on="on"
                                  />
                                </template>
                                <span>Please call (561) 607-5051 for any questions regarding your subscription or for cancellation.</span>
                              </v-tooltip>
                            </span>
                          </p>
                        </div>
                        <div class="leads-subs-zip-cancel-container">
                          <p class="leads-sbus-zip-notes">
                            Started on: <span>{{ updateSubscriptionDate(item.start_date) }}</span>
                          </p>
                          <div
                            v-if="item.renews === 0"
                          >
                            <p class="leads-sbus-zip-notes">
                              You have cancelled your subscription.
                            </p>
                            <p class="leads-sbus-zip-notes">
                              Subscription is valid until:
                              <br>
                              <span>
                                {{ updateSubscriptionDate(item.end_date) }}
                              </span>
                            </p>
                          </div>
                          <div
                            v-else
                          >
                            <p class="leads-sbus-zip-notes">
                              Renews on:
                              <span>
                                {{ updateSubscriptionDate(item.end_date) }}
                              </span>
                            </p>
                            <!-- <button
                          @click="cancelSlotSubscription(item.subscription_id, agent_id, item.zipcode, agent)"
                        >
                          Cancel Subscription
                        </button> -->
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </v-tab-item>
              <v-tab-item v-if="isLender">
                <div class="leads-subs-section top">
                  <h2 class="btn-subscription-details-heading">
                    Buy a share of the leads in your zip code(s)
                  </h2>
                  <div class="btn-subscription-details">
                    <!-- Search for slots availability by zity or zipcode: -->
                    <!-- <v-text-field
                v-model="leadSlotSearchInput"
                class="centered-input"
                outlined
                placeholder="Search for slots availability by city or zipcode:"
                style="width:50%; margin-left:25%; text-align: center;max-height: 65px"
                />
                <button
                class="btn-subscription-details-heading"
                @click="fetchZipSlotCount()"
                >
                  Search
                </button> -->
                    <div
                      v-if="freeSlotsRemainingL > 0"
                    >
                      <p>You have {{ freeSlotsRemainingL }} slot subscription(s) with free trial remaining.</p>
                    </div>
                    <div>
                      <search-suggestions-lead-slots
                        :user-type="lender"
                        @fetchSlotCount="fetchSlotCount"
                      />
                    </div>
                    <div
                      v-if="zipSlotRecordsL.length > 0 && $vuetify.breakpoint.mdAndUp"
                    >
                      <div
                        class="leads-subs-search-results flex-wrapper"
                      >
                        <div class="flex-col-md-4">
                          <div class="full">
                            <span class="heading-font">Zipcode</span>
                          </div>
                        </div>
                        <div class="flex-col-md-4">
                          <div class="full">
                            <span class="heading-font">Number Of Slots Available</span>
                          </div>
                        </div>
                        <div class="flex-col-md-4">
                          <div class="full">
                            <span class="heading-font">Price</span>
                          </div>
                        </div>
                      </div>
                      <div
                        v-if="zipSlotRecordsL.length > 0"
                      >
                        <div
                          v-for="item in zipSlotRecordsL"
                          :key="item.zipcode"
                          class="flex-wrapper"
                        >
                          <div class="flex-col-md-4 mt-8">
                            <span class="zip-font">{{ item.zipcode }}</span>
                          </div>
                          <div class="flex-col-md-4 mt-8">
                            <span>
                              <i
                                v-if="item.slotsAvailable < 3"
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                aria-hidden="true"
                              />
                              <i
                                v-else
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: #06A550; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                aria-hidden="true"
                              />
                            </span>
                            <span>
                              <i
                                v-if="item.slotsAvailable < 2"
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                aria-hidden="true"
                              />
                              <i
                                v-else
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: #06A550; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                aria-hidden="true"
                              />
                            </span>
                            <span>
                              <i
                                v-if="item.slotsAvailable < 1"
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                aria-hidden="true"
                              />
                              <i
                                v-else
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: #06A550; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                aria-hidden="true"
                              />
                            </span>
                          </div>
                          <div class="flex-col-md-4 mt-8">
                            <div v-if="item.slotsAvailable > 0 && !isInOwnedSlots(item.zipcode, lender)">
                              <div v-if="freeSlotsRemainingL === 0">
                                <span class="price-font">$20.00</span>
                                <button
                                  v-if="!isZipInCart(item.zipcode, lender)"
                                  class="btn-subscription-details-heading"
                                  style="margin-left: 20px"
                                  @click="addZipToCart(item.zipcode, lender)"
                                >
                                  Subscribe Now
                                </button>
                              </div>
                              <div v-if="freeSlotsRemainingL > 0">
                                <button
                                  v-if="!isZipInCart(item.zipcode, lender)"
                                  class="btn-subscription-details-heading"
                                  style="margin-left: 20px"
                                  :disabled="refreshNeeded || !agreedLicensing"
                                  @click="doSlotFreeTrialSubscription(item.zipcode, lender)"
                                >
                                  Subscribe For Free Trial
                                </button>
                                <p
                                  v-if="refreshNeeded"
                                  style="color: red"
                                >
                                  Error occurred. Please refresh the page.
                                </p>
                              </div>
                            </div>
                            <div v-else>
                              <div
                                v-if="isInOwnedSlots(item.zipcode, lender)"
                              >
                                <span>Subscribed!</span>
                              </div>
                              <div v-else>
                                No Slots Available!
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div
                      v-if="zipSlotRecordsL.length > 0 && $vuetify.breakpoint.smAndDown"
                    >
                      <div
                        v-for="item in zipSlotRecords"
                        :key="item.zipcode"
                        class="flex-wrapper"
                      >
                        <div class="flex-col-md-5">
                          <div class="full">
                            <span><b>Zipcode</b></span>
                          </div>
                          <div class="full">
                            <span><b>Slots</b></span>
                          </div>
                          <div class="full">
                            <span><b>Price</b></span>
                          </div>
                        </div>
                        <div class="flex-col-md-7">
                          <div class="full">
                            <span class="zip-font">{{ item.zipcode }}</span>
                          </div>
                          <div class="full">
                            <!-- {{ item.slotsAvailable }} -->
                            <span>
                              <i
                                v-if="item.slotsAvailable < 3"
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: grey;'"
                                aria-hidden="true"
                              />
                              <i
                                v-else
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: #06A550;'"
                                aria-hidden="true"
                              />
                            </span>
                            <span>
                              <i
                                v-if="item.slotsAvailable < 2"
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: grey;'"
                                aria-hidden="true"
                              />
                              <i
                                v-else
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: #06A550;'"
                                aria-hidden="true"
                              />
                            </span>
                            <span>
                              <i
                                v-if="item.slotsAvailable < 1"
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: grey;'"
                                aria-hidden="true"
                              />
                              <i
                                v-else
                                :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                :style="'color: #06A550;'"
                                aria-hidden="true"
                              />
                            </span>
                          </div>
                          <div class="full">
                            <div v-if="item.slotsAvailable > 0 && !isInOwnedSlots(item.zipcode)">
                              <div v-if="freeSlotsRemainingL === 0">
                                <span class="zip-font">$20.00</span>
                                <button
                                  v-if="!isZipInCart(item.zipcode, lender)"
                                  class="btn-subscription-details-heading"
                                  style="margin-left: 5px"
                                  @click="addZipToCart(item.zipcode, lender)"
                                >
                                  Subscribe Now
                                </button>
                                <!-- <button
                              v-if="isZipInCart(item.zipcode)"
                              color="red"
                              style="margin-left: 20px"
                              class="white--text"
                              @click="removeZipFromCart(item.zipcode)"
                              >
                                Remove
                              </button> -->
                              </div>
                              <div v-if="freeSlotsRemainingL > 0">
                                <button
                                  v-if="!isZipInCart(item.zipcode, lender)"
                                  class="btn-subscription-details-heading"
                                  :disabled="refreshNeeded || !agreedLicensing"
                                  @click="doSlotFreeTrialSubscription(item.zipcode, lender)"
                                >
                                  Free Trial
                                </button>
                                <p
                                  v-if="refreshNeeded"
                                  style="color: red"
                                >
                                  Error occurred. Please refresh the page.
                                </p>
                              </div>
                            </div>
                            <div v-else>
                              <div
                                v-if="isInOwnedSlots(item.zipcode, lender)"
                              >
                                <span>Subscribed!</span>
                              </div>
                              <div v-else>
                                No Slots Available!
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div style="text-align:center">
                    <br>
                    <input
                      v-model="agreedLicensing"
                      type="checkbox"
                    >
                    <label>
                      By checking here you agree to Honely's Lead Licensing Agreement.
                    </label>
                  </div>
                </div>
                <br>
                <div class="leads-subs-section bottom">
                  <h2
                    class="btn-subscription-details-heading"
                  >
                    My Zip Codes
                  </h2>
                  <div class="btn-subscription-details">
                    <div
                      v-if="slotSubscriptionsLoaded && slotSubscriptionDetailsL.length === 0"
                    >
                      <p>
                        No Active Zip Codes
                      </p>
                    </div>
                    <!-- vx: check neten's object and coorect below part (zipcode etc) -->
                    <div
                      v-else
                      class="leads-subs-zipcodes"
                    >
                      <div
                        v-for="item in slotSubscriptionDetailsL"
                        :key="item.zipcode"
                        class="leads-subs-zip"
                      >
                        <div class="leads-subs-zip-info-container">
                          <p class="leads-subs-zip-title">
                            <i class="fas fa-map-marker-alt" /> {{ item.zipcode }} <span style="float: right;">
                              <v-tooltip
                                content-class="custom-tooltip"
                                bottom
                                max-width="300px"
                              >
                                <template #activator="{ on, attrs }">
                                  <i
                                    class="fas fa-info-circle"
                                    v-bind="attrs"
                                    v-on="on"
                                  />
                                </template>
                                <span>Please call (561) 607-5051 for any questions regarding your subscription or for cancellation.</span>
                              </v-tooltip>
                            </span>
                          </p>
                        </div>
                        <div class="leads-subs-zip-cancel-container">
                          <p class="leads-sbus-zip-notes">
                            Started on: <span>{{ updateSubscriptionDate(item.start_date) }}</span>
                          </p>
                          <div
                            v-if="item.renews === 0"
                          >
                            <p class="leads-sbus-zip-notes">
                              You have cancelled your subscription.
                            </p>
                            <p class="leads-sbus-zip-notes">
                              Subscription is valid until:
                              <br>
                              <span>
                                {{ updateSubscriptionDate(item.end_date) }}
                              </span>
                            </p>
                          </div>
                          <div
                            v-else
                          >
                            <p class="leads-sbus-zip-notes">
                              Renews on:
                              <span>
                                {{ updateSubscriptionDate(item.end_date) }}
                              </span>
                            </p>
                            <!-- <button
                              @click="cancelSlotSubscription(item.subscription_id, agent_id, item.zipcode, lender)"
                            >
                              Cancel Subscription
                            </button> -->
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </v-tab-item>
              <v-tab-item v-if="isGeneralContractor">
                <div class="leads-subs-section top">
                  <h2 class="btn-subscription-details-heading">
                    Buy a share of the leads in your zip code(s)
                  </h2>
                  <div class="btn-subscription-details">
                    <!-- Search for slots availability by zity or zipcode: -->
                    <!-- <v-text-field
                v-model="leadSlotSearchInput"
                class="centered-input"
                outlined
                placeholder="Search for slots availability by city or zipcode:"
                style="width:50%; margin-left:25%; text-align: center;max-height: 65px"
                />
                <button
                class="btn-subscription-details-heading"
                @click="fetchZipSlotCount()"
                >
                  Search
                </button> -->
                    <div
                      v-if="freeSlotsRemainingGC > 0"
                    >
                      <p>You have {{ freeSlotsRemainingGC }} slot subscription(s) with free trial remaining.</p>
                    </div>
                    <div>
                      <search-suggestions-lead-slots
                        :user-type="gc"
                        @fetchSlotCount="fetchSlotCount"
                      />
                    </div>
                    <div
                      v-if="zipSlotRecordsGC.length > 0 && $vuetify.breakpoint.mdAndUp"
                    >
                      <v-row
                        class="row-margins leads-subs-search-results"
                      >
                        <v-col
                          :md="3.5"
                        >
                          <v-row>
                            <v-col
                              :md="12"
                            >
                              <span class="heading-font">Zipcode</span>
                            </v-col>
                          </v-row>
                        </v-col>
                        <v-col
                          :md="3.5"
                        >
                          <v-row>
                            <v-col
                              :md="12"
                            >
                              <span class="heading-font">Number Of Slots Available</span>
                            </v-col>
                          </v-row>
                        </v-col>
                        <v-col
                          :md="3.5"
                        >
                          <v-row>
                            <v-col
                              :md="12"
                            >
                              <span class="heading-font">Price</span>
                            </v-col>
                          </v-row>
                        </v-col>
                      </v-row>
                      <div
                        v-if="zipSlotRecordsGC.length > 0"
                      >
                        <br>
                        <v-row
                          v-for="item in zipSlotRecordsGC"
                          :key="item.zipcode"
                          class="row-margins"
                        >
                          <v-col
                            :md="3.5"
                          >
                            <span class="zip-font">{{ item.zipcode }}</span>
                          </v-col>
                          <v-col
                            :md="3.5"
                          >
                            <v-row>
                              <v-col
                                :md="12"
                              >
                                <!-- {{ item.slotsAvailable }} -->
                                <span>
                                  <i
                                    v-if="item.slotsAvailable < 3"
                                    :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                    :style="'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                    aria-hidden="true"
                                  />
                                  <i
                                    v-else
                                    :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                    :style="'color: #06A550; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                    aria-hidden="true"
                                  />
                                </span>
                                <span>
                                  <i
                                    v-if="item.slotsAvailable < 2"
                                    :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                    :style="'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                    aria-hidden="true"
                                  />
                                  <i
                                    v-else
                                    :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                    :style="'color: #06A550; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                    aria-hidden="true"
                                  />
                                </span>
                                <span>
                                  <i
                                    v-if="item.slotsAvailable < 1"
                                    :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                    :style="'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                    aria-hidden="true"
                                  />
                                  <i
                                    v-else
                                    :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                    :style="'color: #06A550; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                                    aria-hidden="true"
                                  />
                                </span>
                              </v-col>
                            </v-row>
                          </v-col>
                          <v-col
                            :md="3.5"
                          >
                            <div v-if="item.slotsAvailable > 0 && !isInOwnedSlots(item.zipcode, gc)">
                              <div v-if="freeSlotsRemainingGC === 0">
                                <span class="price-font">$20.00</span>
                                <button
                                  v-if="!isZipInCart(item.zipcode, gc)"
                                  class="btn-subscription-details-heading"
                                  style="margin-left: 20px"
                                  @click="addZipToCart(item.zipcode, gc)"
                                >
                                  Subscribe Now
                                </button>
                                <!-- <button
                            v-if="isZipInCart(item.zipcode)"
                            color="red"
                            style="margin-left: 20px"
                            class="white--text"
                            @click="removeZipFromCart(item.zipcode)"
                            >
                              Remove
                            </button> -->
                              </div>
                              <div v-if="freeSlotsRemainingGC > 0">
                                <button
                                  v-if="!isZipInCart(item.zipcode, gc)"
                                  class="btn-subscription-details-heading"
                                  style="margin-left: 20px"
                                  :disabled="refreshNeeded || !agreedLicensing"
                                  @click="doSlotFreeTrialSubscription(item.zipcode, gc)"
                                >
                                  Subscribe For Free Trial
                                </button>
                                <p
                                  v-if="refreshNeeded"
                                  style="color: red"
                                >
                                  Error occurred. Please refresh the page.
                                </p>
                              </div>
                            </div>
                            <div v-else>
                              <div
                                v-if="isInOwnedSlots(item.zipcode, gc)"
                              >
                                <span>Subscribed!</span>
                              </div>
                              <div v-else>
                                No Slots Available!
                              </div>
                            </div>
                          </v-col>
                        </v-row>
                      </div>
                    </div>
                    <div
                      v-if="zipSlotRecordsGC.length > 0 && $vuetify.breakpoint.smAndDown"
                    >
                      <br>
                      <v-row
                        v-for="item in zipSlotRecordsGC"
                        :key="item.zipcode"
                        class="row-margins"
                      >
                        <v-col
                          :md="5"
                        >
                          <v-row>
                            <v-col
                              :md="12"
                            >
                              <span><b>Zipcode</b></span>
                            </v-col>
                          </v-row>
                          <v-row>
                            <v-col
                              :md="12"
                            >
                              <span><b>Slots</b></span>
                            </v-col>
                          </v-row>
                          <v-row>
                            <v-col
                              :md="12"
                            >
                              <span><b>Price</b></span>
                            </v-col>
                          </v-row>
                        </v-col>
                        <v-col
                          :md="7"
                        >
                          <v-row>
                            <v-col>
                              <span class="zip-font">{{ item.zipcode }}</span>
                            </v-col>
                          </v-row>
                          <v-row>
                            <v-col
                              :md="12"
                            >
                              <!-- {{ item.slotsAvailable }} -->
                              <span>
                                <i
                                  v-if="item.slotsAvailable < 3"
                                  :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                  :style="'color: grey;'"
                                  aria-hidden="true"
                                />
                                <i
                                  v-else
                                  :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                  :style="'color: #06A550;'"
                                  aria-hidden="true"
                                />
                              </span>
                              <span>
                                <i
                                  v-if="item.slotsAvailable < 2"
                                  :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                  :style="'color: grey;'"
                                  aria-hidden="true"
                                />
                                <i
                                  v-else
                                  :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                  :style="'color: #06A550;'"
                                  aria-hidden="true"
                                />
                              </span>
                              <span>
                                <i
                                  v-if="item.slotsAvailable < 1"
                                  :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                  :style="'color: grey;'"
                                  aria-hidden="true"
                                />
                                <i
                                  v-else
                                  :class="'mdi header-icon mdi-account text-h5 box-headline'"
                                  :style="'color: #06A550;'"
                                  aria-hidden="true"
                                />
                              </span>
                            </v-col>
                          </v-row>
                          <v-row>
                            <v-col
                              :md="12"
                            >
                              <div v-if="item.slotsAvailable > 0 && !isInOwnedSlots(item.zipcode, gc)">
                                <div v-if="freeSlotsRemainingGC === 0">
                                  <span class="zip-font">$20.00</span>
                                  <button
                                    v-if="!isZipInCart(item.zipcode, gc)"
                                    class="btn-subscription-details-heading"
                                    style="margin-left: 5px"
                                    @click="addZipToCart(item.zipcode, gc)"
                                  >
                                    Subscribe Now
                                  </button>
                                  <!-- <button
                              v-if="isZipInCart(item.zipcode)"
                              color="red"
                              style="margin-left: 20px"
                              class="white--text"
                              @click="removeZipFromCart(item.zipcode)"
                              >
                                Remove
                              </button> -->
                                </div>
                                <div v-if="freeSlotsRemainingGC > 0">
                                  <button
                                    v-if="!isZipInCart(item.zipcode, gc)"
                                    class="btn-subscription-details-heading"
                                    :disabled="refreshNeeded || !agreedLicensing"
                                    @click="doSlotFreeTrialSubscription(item.zipcode, gc)"
                                  >
                                    Free Trial
                                  </button>
                                  <p
                                    v-if="refreshNeeded"
                                    style="color: red"
                                  >
                                    Error occurred. Please refresh the page.
                                  </p>
                                </div>
                              </div>
                              <div v-else>
                                <div
                                  v-if="isInOwnedSlots(item.zipcode, gc)"
                                >
                                  <span>Subscribed!</span>
                                </div>
                                <div v-else>
                                  No Slots Available!
                                </div>
                              </div>
                            </v-col>
                          </v-row>
                        </v-col>
                      </v-row>
                    </div>
                  </div>
                  <div style="text-align:center">
                    <br>
                    <input
                      v-model="agreedLicensing"
                      type="checkbox"
                    >
                    <label>
                      By checking here you agree to Honely's Lead Licensing Agreement.
                    </label>
                  </div>
                </div>
                <br>
                <div class="leads-subs-section bottom">
                  <h2
                    class="btn-subscription-details-heading"
                  >
                    My Zip Codes
                  </h2>
                  <div class="btn-subscription-details">
                    <div
                      v-if="slotSubscriptionsLoaded && slotSubscriptionDetailsGC.length === 0"
                    >
                      <p>
                        No Active Zip Codes
                      </p>
                    </div>
                    <!-- vx: check neten's object and coorect below part (zipcode etc) -->
                    <div
                      v-else
                      class="leads-subs-zipcodes"
                    >
                      <div
                        v-for="item in slotSubscriptionDetailsGC"
                        :key="item.zipcode"
                        class="leads-subs-zip"
                      >
                        <div class="leads-subs-zip-info-container">
                          <p class="leads-subs-zip-title">
                            <i class="fas fa-map-marker-alt" /> {{ item.zipcode }} <span style="float: right;">
                              <v-tooltip
                                content-class="custom-tooltip"
                                bottom
                                max-width="300px"
                              >
                                <template #activator="{ on, attrs }">
                                  <i
                                    class="fas fa-info-circle"
                                    v-bind="attrs"
                                    v-on="on"
                                  />
                                </template>
                                <span>Please call (561) 607-5051 for any questions regarding your subscription or for cancellation.</span>
                              </v-tooltip>
                            </span>
                          </p>
                        </div>
                        <div class="leads-subs-zip-cancel-container">
                          <p class="leads-sbus-zip-notes">
                            Started on: <span>{{ updateSubscriptionDate(item.start_date) }}</span>
                          </p>
                          <div
                            v-if="item.renews === 0"
                          >
                            <p class="leads-sbus-zip-notes">
                              You have cancelled your subscription.
                            </p>
                            <p class="leads-sbus-zip-notes">
                              Subscription is valid until:
                              <br>
                              <span>
                                {{ updateSubscriptionDate(item.end_date) }}
                              </span>
                            </p>
                          </div>
                          <div
                            v-else
                          >
                            <p class="leads-sbus-zip-notes">
                              Renews on:
                              <span>
                                {{ updateSubscriptionDate(item.end_date) }}
                              </span>
                            </p>
                            <!-- <button
                              @click="cancelSlotSubscription(item.subscription_id, agent_id, item.zipcode, gc)"
                            >
                              Cancel Subscription
                            </button> -->
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </v-tab-item>
            </v-tabs>
          </v-tab-item>
          <v-tab-item
            value="tab-1"
          >
            <!-- <div>
            <circle-spin :loading='true'></circle-spin>
            </div> -->
            <!-- vx: the guy hasn't subscribed yet -->
            <div
              v-if="showButtonSubscribeSection"
              class="leads-subs-section top"
            >
              <h2
                class="btn-subscription-details-heading"
              >
                Subscribe For The 'Future Value' Button!
              </h2>
              <v-row
                align="center"
              >
                <v-col
                  v-if="$vuetify.breakpoint.mdAndUp"
                  :md="3"
                />
                <v-col
                  :md="$vuetify.breakpoint.mdAndUp?'6':'12'"
                >
                  <p
                    class="btn-subscription-details lage-font"
                  >
                    Subscribe to have access to predictive analytics on your real estate site! For a small price of $99.99 per month, capture more leads and increase user engagement on your site with the "Future Value" button.
                  </p>
                </v-col>
                <v-col
                  v-if="$vuetify.breakpoint.mdAndUp"
                  :md="3"
                />
              </v-row>
              <div>
                <div style="text-align: center">
                  <span class="btn-subscription-details lage-font">Select The Type Of Your Company:</span>
                  <br>
                  <div :style="!$vuetify.breakpoint.smAndDown?'margin-top: 10px;':''">
                    <div class="form_radio_btn">
                      <input
                        id="sole-proprietorship"
                        v-model="companyType"
                        type="radio"
                        value="Sole Proprietorship"
                        style="margin-left: 10px;"
                      >
                      <label for="sole-proprietorship">Sole Proprietorship</label>&nbsp;
                    </div>
                    <br v-if="$vuetify.breakpoint.smAndDown">
                    <div class="form_radio_btn">
                      <input
                        id="corporation"
                        v-model="companyType"
                        type="radio"
                        value="Corporation"
                        style="margin-left: 10px;"
                      >
                      <label for="corporation">Corporation</label>&nbsp;
                    </div>
                    <br v-if="$vuetify.breakpoint.smAndDown">
                    <!-- </div>
                  <div> -->
                    <div class="form_radio_btn">
                      <input
                        id="limited-liability-company"
                        v-model="companyType"
                        type="radio"
                        value="Limited Liability Company"
                        style="margin-left: 10px;"
                      >
                      <label for="limited-liability-company">Limited Liability Company</label>&nbsp;
                    </div>
                    <br v-if="$vuetify.breakpoint.smAndDown">
                    <!-- </div>
                <div> -->
                    <div class="form_radio_btn">
                      <input
                        id="partnership"
                        v-model="companyType"
                        type="radio"
                        value="Partnership"
                        style="margin-left: 10px;"
                      >
                      <label for="partnership">Partnership</label>&nbsp;
                    </div>
                    <br v-if="$vuetify.breakpoint.smAndDown">
                  </div>
                </div>
                <br>
                <v-text-field
                  v-model="enteredBusinessName"
                  label=""
                  placeholder="Enter the name of the Service Provider or the Business"
                  outlined
                  class="auth-modal-form-control"
                  :style="$vuetify.breakpoint.smAndDown?'width: 90%; margin-left: 5%':'width:50%; margin-left:25%'"
                />
                <v-text-field
                  v-model="enteredBusinessAddress"
                  label=""
                  placeholder="Enter your Business' Address"
                  outlined
                  class="auth-modal-form-control"
                  :style="$vuetify.breakpoint.smAndDown?'width: 90%; margin-left: 5%':'width:50%; margin-left:25%'"
                />
                <v-text-field
                  v-model="enteredHomeUrl"
                  label=""
                  placeholder="Paste the URL where you will be placing the 'Future Value' Button"
                  outlined
                  class="auth-modal-form-control"
                  :style="$vuetify.breakpoint.smAndDown?'width: 90%; margin-left: 5%':'width:50%; margin-left:25%'"
                />
                <div
                  class="check-box-section"
                >
                  <v-checkbox
                    v-model="isAuthorizedRep"
                    label=""
                    class="auth-modal-form-control"
                  />
                  <label>&nbsp;I confirm that I am an authorized representative of this business</label>
                </div>
                <div style="text-align: center; margin-top: 5px;">
                  <span style="color: red">{{ urlPrompt }}</span>
                </div>
              </div>
              <div class="btn-center">
                <button
                  class="btn-subscription"
                  @click="doPayment()"
                >
                  Subscribe Now
                </button>
              </div>
            </div>
            <!-- vx: The guy has subscribed for lead generation link -->
            <div
              v-if="showButtonSubscriptionDetails"
            >
              <h2
                class="btn-subscription-details-heading"
              >
                Your <strong>'Future Value'</strong> Button Subscription Details:
              </h2>
              <p
                class="btn-subscription-details"
              >
                Redirect link:<br v-if="$vuetify.breakpoint.smAndDown">
                <span style="color: #06A550; margin-left: 10px;">
                  https://www.realtor-leads.info/?agent_id={{ agent_id }}
                </span>
              </p>
              <p class="btn-subscription-details-small-margin-bottom">Code to embed to Future Value page:</p>
              <div class="btn-code-snipppet">
                &lt;iframe src="https://www.realtor-leads.info/?agent_id={{ agent_id }}" height="600" width="100%" title="Find the future value of your home"&gt;&lt;/iframe&gt;
              </div>
              <p
                class="btn-subscription-details"
              >
                Subscription started on:
                <br v-if="$vuetify.breakpoint.smAndDown">
                <span style="color: #06A550; margin-left: 10px;">
                  {{ btnSubscriptionStartDate }}
                </span>
              </p>
              <div v-if="!showButtonUnsubscribeBtn">
                <p
                  class="btn-subscription-details"
                >
                  You have cancelled your subscription.
                  <br v-if="$vuetify.breakpoint.smAndDown">
                  Current subscription is valid until:
                  <br v-if="$vuetify.breakpoint.smAndDown">
                  <span style="color: #06A550; margin-left: 10px;">
                    {{ btnSubscriptionEndDate }}
                  </span>
                </p>
              </div>
              <div
                v-if="showButtonUnsubscribeBtn"
              >
                <p class="btn-subscription-details">
                  Subscription renews on:
                  <br v-if="$vuetify.breakpoint.smAndDown">
                  <span style="color: #06A550; margin-left: 10px;">
                    {{ btnSubscriptionRenewalDate }}
                  </span>
                </p>
                <!-- <button
                  color="red"
                  class="white-text"
                  style="margin-left: 45%"
                  @click="cancelButtonSubscription()"
                >
                  Cancel Subscription
                </button> -->
              </div>
            </div>
          </v-tab-item>
          <!-- <v-tab-item>
            <br>
      <v-combobox
        ref="search"
        v-bind="attrs"
        background-color="white"
        class="suggestions-field"
        clearable
        color="#000000"
        dense
        light
        filled
        hide-no-data
        hide-selected
        item-color="#7c7c7c"
        item-text="name"
        item-value="name"
        outlined
        placeholder="Search city or zip code"
        append-outer-icon="mdi-magnify"
        append-icon=""
        return-object
        v-on="on"
        height="52px"
        full-width
        no-filter
      >
      </v-combobox>
      <br>
      <div
      v-if="$vuetify.breakpoint.mdAndUp"
      >
      <v-row
      class="row-margins"
      >
        <v-col
        :md="1"
        >
        <v-row>
          <v-col
          :md="12"
          align="center"
        justify="center"
          >
            <span class='heading-font'>Zipcode</span>
          </v-col>
        </v-row>
        </v-col>
        <v-col
        :md="8"
        >
        <v-row>
          <v-col
          :md="3.5"
          align="center"
        justify="center"
          >
          <span class='heading-font'>Realtors</span>
          </v-col>
          <v-col
          :md="3.5"
          align="center"
        justify="center"
          >
          <span class='heading-font'>Lenders</span>
          </v-col>
          <v-col
          :md="3.5"
          align="center"
        justify="center"
          >
          <span class='heading-font'>Contractors</span>
          </v-col>
        </v-row>
        </v-col>
        <v-col
        :md="3"
        >
        <v-row>
          <v-col
          :md="12"
          align="center"
        justify="center"
          >
          <span class='heading-font'>Price</span>
          </v-col>
        </v-row>
        </v-col>
      </v-row>
      <v-row
      class="row-margins"
      style="margin-bottom: 5px"
      >
        <v-col
        :md="1"
        align="center"
        justify="center"
        >
        <span class="zip-font">000000</span>
        </v-col>
        <v-col
        :md="8"
        >
        <v-row>
          <v-col
          :md="3.5"
          align="center"
        justify="center"
          >
          <i
                          :class="'mdi header-icon mdi-account text-h5 box-headline'"
                          :style="$vuetify.breakpoint.name === 'md' ? 'color: grey; font-size: 30px !important; margin-left: 5px; margin-right: 5px':'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                          aria-hidden="true"
                        />
                        <i
                          :class="'mdi header-icon mdi-account text-h5 box-headline'"
                          :style="$vuetify.breakpoint.name === 'md' ? 'color: grey; font-size: 30px !important; margin-left: 5px; margin-right: 5px':'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                          aria-hidden="true"
                        />
          <i
                          :class="'mdi header-icon mdi-account-plus text-h5 box-headline'"
                          :style="$vuetify.breakpoint.name === 'md' ? 'color: #06A550; font-size: 30px !important; margin-left: 5px; margin-right: 5px':'color: #06A550; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                          aria-hidden="true"
                        />
          </v-col>
          <v-col
          :md="3.5"
          align="center"
        justify="center"
          >
          <i
                          :class="'mdi header-icon mdi-account text-h5 box-headline slot'"
                          :style="$vuetify.breakpoint.name === 'md' ? 'color: grey; font-size: 30px !important; margin-left: 5px; margin-right: 5px':'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                          aria-hidden="true"
                        />
                        <i
                          :class="'mdi header-icon mdi-account text-h5 box-headline slot'"
                          :style="$vuetify.breakpoint.name === 'md' ? 'color:grey; font-size: 30px !important; margin-left: 5px; margin-right: 5px':'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                          aria-hidden="true"
                        />
          <i
                          :class="'mdi header-icon mdi-account-plus text-h5 box-headline slot'"
                          :style="$vuetify.breakpoint.name === 'md' ? 'color: #06A550; font-size: 30px !important; margin-left: 5px; margin-right: 5px':'color: #06A550; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                          aria-hidden="true"
                        />
          </v-col>
          <v-col
          :md="3.5"
          align="center"
        justify="center"
          >
          <i
                          :class="'mdi header-icon mdi-account text-h5 box-headline slot'"
                          :style="$vuetify.breakpoint.name === 'md' ? 'color: grey; font-size: 30px !important; margin-left: 5px; margin-right: 5px':'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                          aria-hidden="true"
                        />
                        <i
                          :class="'mdi header-icon mdi-account text-h5 box-headline slot'"
                          :style="$vuetify.breakpoint.name === 'md' ? 'color: grey; font-size: 30px !important; margin-left: 5px; margin-right: 5px':'color: grey; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                          aria-hidden="true"
                        />
          <i
                          :class="'mdi header-icon mdi-account-plus text-h5 box-headline slot'"
                          :style="$vuetify.breakpoint.name === 'md' ? 'color: #06A550; font-size: 30px !important; margin-left: 5px; margin-right: 5px':'color: #06A550; font-size: 40px !important; margin-left: 10px; margin-right: 10px'"
                          aria-hidden="true"
                        />
          </v-col>
        </v-row>
        </v-col>
        <v-col
        :md="3"
        align="center"
        justify="center"
        >
        <span class="price-font">$00.00</span>
        <button
        class="btn-subscription-details-heading"
        style="margin-left: 20px"
        >
          Buy now
        </button>
        </v-col>
      </v-row>
      </div>
      <div
      v-if="$vuetify.breakpoint.smAndDown"
      >
      <v-row>
        <v-col
        :md="3.5"
        >
          <span class='heading-font'>Zipcode</span>
        </v-col>
        <v-col
        :md="8"
        >
          <span class="zip-font">000000</span>
        </v-col>
      </v-row>
      <v-row>
        <v-col
        :md="3.5"
        >
          <span class='heading-font'>Realtors</span>
        </v-col>
        <v-col
        :md="8"
        >
        <i
                          :class="'mdi header-icon mdi-account text-h5 box-headline'"
                          :style="'color: grey; font-size: 30px !important; margin-left: 5px; margin-right: 5px'"
                          aria-hidden="true"
                        />
                        <i
                          :class="'mdi header-icon mdi-account text-h5 box-headline'"
                          :style="'color: grey; font-size: 30px !important; margin-left: 5px; margin-right: 5px'"
                          aria-hidden="true"
                        />
          <i
                          :class="'mdi header-icon mdi-account-plus text-h5 box-headline'"
                          :style="'color: #06A550; font-size: 30px !important; margin-left: 5px; margin-right: 5px'"
                          aria-hidden="true"
                        />
        </v-col>
      </v-row>
      <v-row>
        <v-col
        :md="3.5"
        >
          <span class='heading-font'>Lenders</span>
        </v-col>
        <v-col
        :md="8"
        >
        <i
                          :class="'mdi header-icon mdi-account text-h5 box-headline'"
                          :style="'color: grey; font-size: 30px !important; margin-left: 5px; margin-right: 5px'"
                          aria-hidden="true"
                        />
                        <i
                          :class="'mdi header-icon mdi-account text-h5 box-headline'"
                          :style="'color: grey; font-size: 30px !important; margin-left: 5px; margin-right: 5px'"
                          aria-hidden="true"
                        />
          <i
                          :class="'mdi header-icon mdi-account-plus text-h5 box-headline'"
                          :style="'color: #06A550; font-size: 30px !important; margin-left: 5px; margin-right: 5px'"
                          aria-hidden="true"
                        />
        </v-col>
      </v-row>
      <v-row>
        <v-col
        :md="3.5"
        >
          <span class='heading-font'>Contractors</span>
        </v-col>
        <v-col
        :md="8"
        >
        <i
                          :class="'mdi header-icon mdi-account text-h5 box-headline'"
                          :style="'color: grey; font-size: 30px !important; margin-left: 5px; margin-right: 5px'"
                          aria-hidden="true"
                        />
                        <i
                          :class="'mdi header-icon mdi-account text-h5 box-headline'"
                          :style="'color: grey; font-size: 30px !important; margin-left: 5px; margin-right: 5px'"
                          aria-hidden="true"
                        />
          <i
                          :class="'mdi header-icon mdi-account-plus text-h5 box-headline'"
                          :style="'color: #06A550; font-size: 30px !important; margin-left: 5px; margin-right: 5px'"
                          aria-hidden="true"
                        />
        </v-col>
      </v-row>
      <v-row>
        <v-col
        :md="3.5"
        >
          <span class='heading-font'>Price</span>
        </v-col>
        <v-col
        :md="8"
        >
          <span class="zip-font">$00.00</span>
        <button
        class="btn-subscription-details-heading"
        style="margin-left: 20px"
        >
          Buy now
        </button>
        </v-col>
      </v-row>
      </div>
          </v-tab-item> -->
        </v-tabs>
      </div>
    </div>
    <stripe-checkout
      ref="checkoutRef"
      :customer-email="cognitoUser.attributes.email"
      mode="subscription"
      :pk="publishableKey"
      :line-items="lineItems"
      :success-url="successURL"
      :cancel-url="cancelURL"
      @loading="v => loading = v"
    />
  </div>
</template>
<script>
  import { StripeCheckout } from '@vue-stripe/vue-stripe'
  import axios from 'axios'
  import {
    mapGetters,
  } from 'vuex'
  import { bus } from '@/main'
  export default {
    components: {
      LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
      StripeCheckout,
      SearchSuggestionsLeadSlots: () => import('@/components/SearchSuggestionsLeadSlots'),
    },
    data () {
      return {
        agent: 'AGENT/BROKER',
        lender: 'LENDER',
        gc: 'GENERAL CONTRACTOR',
        isAgent: false,
        isLender: false,
        isGeneralContractor: false,
        agreedLicensing: true,
        refreshNeeded: false,
        customerId: 'cus_Kpcon4obPmqUC8',
        // zipSlotRecords: [],
        // zipSlotRecords: [
        //   {
        //     zipcode: '33434',
        //     slotsAvailable: 2,
        //   },
        //   {
        //     zipcode: '33499',
        //     slotsAvailable: 2,
        //   },
        // ],
        zipSlotRecordsA: [],
        zipSlotRecordsL: [],
        zipSlotRecordsGC: [],
        zipCartA: [],
        zipCartL: [],
        zipCartGC: [],
        agentOwnedZipcodes: [],
        zipPurchasePowerA: null,
        zipPurchasePowerL: null,
        zipPurchasePowerGC: null,
        ownedSlotsA: [],
        ownedSlotsL: [],
        ownedSlotsGC: [],
        urlPrompt: '',
        // selectedTab: '2',
        // tabs: [
        //   { id: '1', name: 'Dashboard'},
        //   { id: '2', name: 'Buy Leads'}
        // ],
        headers: [
          // {
          //   text: 'Dessert (100g serving)',
          //   align: 'start',
          //   sortable: true,
          //   value: 'name',
          // },
          { text: 'First Name', value: 'first_name' },
          { text: 'Last Name', value: 'last_name' },
          { text: 'Email', value: 'user_email' },
          { text: 'Phone Number', value: 'phone_number' },
          { text: 'Searched Address', value: 'searched_address' },
        ],
        site_leads: [],
        button_leads: [],
        agent_id: null,
        loading: true,
        screenSize: this.$vuetify.breakpoint.name,
        publishableKey: 'pk_live_51Jf6iUKnYAv7HjWUdWqgDur9RCxcCQ0LIQJ4UjLbN3ZaagOe2ysT0r44F4CUqkNqOMj1ODml3lnY2A7t1eqmv80q00wet01Ymy',
        successURL: '',
        cancelURL: 'https://www.honely.com/',
        tab: 'tab-3',
        lineItems: [],
        // lineItems: [
        //   {
        //     price: 'price_1K7oHSKnYAv7HjWUJn2RNUhX',
        //     quantity: 1,
        //   },
        // ],
        showButtonSubscribeSection: false,
        showButtonSubscriptionDetails: false,
        showButtonUnsubscribeBtn: false,
        btnSubscriptionStartDate: null,
        btnSubscriptionEndDate: null,
        btnSubscriptionRenewalDate: null,
        btnSubscriptionId: null,
        enteredHomeUrl: '',
        enteredBusinessName: '',
        enteredBusinessAddress: '',
        companyType: null,
        isAuthorizedRep: true,
        agent_name: null,
        agent_first_name: null,
        agent_last_name: null,
        services_list: null,
        agent_email: null,
        // trialUsed: false,
        userType: null,
        leadSlotSearchInput: '',
        // slotSubscriptionDetails: [],
        // vx: testing stuff, remove..
        slotSubscriptionDetailsA: [
          // { email: 'venkat@honely.com', customer_id: 'cus_KJnSahXG1G61aF', subscription_id: 'sub_1Jf9s1KnYAv7HjWU1PYTHMUs', start_date: 'Wed, 29 Sep 2021 00:00:00 GMT', end_date: 'Fri, 29 Oct 2021 00:00:00 GMT', renews: 1, zipcode: '33434', priceID: 'price_1KC9swKnYAv7HjWUOqnJk4wQ' },
          // { email: 'venkat@honely.com', customer_id: 'cus_KJnSahXG1G61aF', subscription_id: 'sub_1Jf9s1KnYAv7HjWU1PYTHMUs', start_date: 'Wed, 29 Sep 2021 00:00:00 GMT', end_date: 'Fri, 29 Oct 2021 00:00:00 GMT', renews: 0, zipcode: '33431', priceID: 'price_1KC9y9KnYAv7HjWUbynQjLN3' },
          // { email: 'venkat@honely.com', customer_id: 'cus_KJnSahXG1G61aF', subscription_id: 'sub_1Jf9s1KnYAv7HjWU1PYTHMUs', start_date: 'Wed, 29 Sep 2021 00:00:00 GMT', end_date: 'Fri, 29 Oct 2021 00:00:00 GMT', renews: 1, zipcode: '33498', priceID: 'price_1KCA0SKnYAv7HjWUl9yAejmf' },
          // { email: 'venkat@honely.com', customer_id: 'cus_KJnSahXG1G61aF', subscription_id: 'sub_1Jf9s1KnYAv7HjWU1PYTHMUs', start_date: 'Wed, 29 Sep 2021 00:00:00 GMT', end_date: 'Fri, 29 Oct 2021 00:00:00 GMT', renews: 1, zipcode: '33429', priceID: 'price_1KCA16KnYAv7HjWUblKKRNTm' },
          // { email: 'venkat@honely.com', customer_id: 'cus_KJnSahXG1G61aF', subscription_id: 'sub_1Jf9s1KnYAv7HjWU1PYTHMUs', start_date: 'Wed, 29 Sep 2021 00:00:00 GMT', end_date: 'Fri, 29 Oct 2021 00:00:00 GMT', renews: 1, zipcode: '33428', priceID: 'price_1KCA2ZKnYAv7HjWU4nEL4Qly' },
          // { email: 'venkat@honely.com', customer_id: 'cus_KJnSahXG1G61aF', subscription_id: 'sub_1Jf9s1KnYAv7HjWU1PYTHMUs', start_date: 'Wed, 29 Sep 2021 00:00:00 GMT', end_date: 'Fri, 29 Oct 2021 00:00:00 GMT', renews: 1, zipcode: '33432', priceID: 'price_1KCA3FKnYAv7HjWUqSOnlqjz' },
          // { email: 'venkat@honely.com', customer_id: 'cus_KJnSahXG1G61aF', subscription_id: 'sub_1Jf9s1KnYAv7HjWU1PYTHMUs', start_date: 'Wed, 29 Sep 2021 00:00:00 GMT', end_date: 'Fri, 29 Oct 2021 00:00:00 GMT', renews: 1, zipcode: '33496', priceID: 'price_1KCA3cKnYAv7HjWUX2R56nsV' },
        ],
        slotSubscriptionDetailsL: [],
        slotSubscriptionDetailsGC: [],
        slotSubscriptionsLoaded: false,
        freeSlotsRemainingA: null,
        freeSlotsRemainingL: null,
        freeSlotsRemainingGC: null,
      }
    },
    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
      zipCartPrice () {
        return (this.zipCart.length * 20) + '.00'
      },
    //   lineItems () {
    //     if (!this.trialUsed) {
    //       return [
    //         {
    //         price: 'price_1K7oHSKnYAv7HjWUJn2RNUhX',
    //         quantity: 1,
    //       },
    //       ]
    //     } else {
    //       return [
    //         {
    //         price: 'price_1K9C7ZKnYAv7HjWUCYVkX9QD',
    //         quantity: 1,
    //       },
    //       ]
    //     }
    //   },
    //   zipPurchasePowerAvailable (userType) {
    //     if (userType === this.agent) {
    //       if ((this.zipPurchasePower - this.zipCartA.length) > 0) {
    //         return true
    //       }
    //     }
    //     if (userType === this.lender) {
    //       if ((this.zipPurchasePower - this.zipCartL.length) > 0) {
    //         return true
    //       }
    //     }
    //     if (userType === this.gc) {
    //       if ((this.zipPurchasePower - this.zipCartGC.length) > 0) {
    //         return true
    //       }
    //     }
    //     return false
    //     // if ((this.zipPurchasePower - this.zipCart.length) > 0) {
    //     //   return true
    //     // } else {
    //     //   return false
    //     // }
    //   },
    // availableSlotClass () {
    //   return this.screenSize === 'md' ? 'slot-available-md' : 'slot-available'
    // },
    // unavailableSlotClass () {
    //   console.log(this.screenSize === 'md' ? 'slot-unavailable-md' : 'slot-unavailable')
    //   return this.screenSize === 'md' ? 'slot-unavailable-md' : 'slot-unavailable'
    // }
    // unavailableSlotClass () {
    //   console.log('vx: unavailableSlotClass computed property callled!!')
    //   switch (this.$vuetify.breakpoint.name) {
    //     case 'md': return 'slot-unavailable-md'
    //     default: return 'slot-unavailable'
    //   }
    // },
    },
    mounted () {
      axios.get('https://api.honely.com/lookup-test/agent_profile?agent_email=' + this.cognitoUser.attributes.email)
        .then((response) => {
          // console.log('vx: agent_profile response', response.data)
          this.agent_id = response.data.agent_id
          this.agent_name = response.data.first_name + ' ' + response.data.last_name
          this.agent_first_name = response.data.first_name
          this.agent_last_name = response.data.last_name
          if (response.data.user_type.includes('AGENT/BROKER')) {
            this.isAgent = true
            this.services_list = 'REALTOR/BROKER'
          }
          if (response.data.user_type.includes('GENERAL CONTRACTOR')) {
            this.isGeneralContractor = true
            this.services_list = 'CONTRACTOR'
          }
          if (response.data.user_type.includes('LENDER')) {
            this.isLender = true
            this.services_list = 'LENDER'
          }
          this.agent_email = response.data.agent_email
          this.enteredHomeUrl = response.data.home_url
          this.enteredBusinessName = response.data.company_name
          this.enteredBusinessAddress = response.data.business_address
          this.companyType = response.data.company_type
          if (this.companyType === 'SOLE PROPRIETORSHIP') {
            this.companyType = 'Sole Proprietorship'
          }
          if (this.companyType === 'CORPORATION') {
            this.companyType = 'Corporation'
          }
          if (this.companyType === 'LIMITED LIABILITY COMPANY') {
            this.companyType = 'Limited Liability Company'
          }
          if (this.companyType === 'PARTNERSHIP') {
            this.companyType = 'Partnership'
          }
          this.userType = response.data.user_type
          this.freeSlotsRemainingA = 5 - parseInt(response.data.free_subscription_count_new.agent)
          this.freeSlotsRemainingL = 5 - parseInt(response.data.free_subscription_count_new.lender)
          this.freeSlotsRemainingGC = 5 - parseInt(response.data.free_subscription_count_new.gc)
          this.zipPurchasePowerA = response.data.slots_left_new.agent
          this.zipPurchasePowerL = response.data.slots_left_new.lender
          this.zipPurchasePowerGC = response.data.slots_left_new.gc
          console.log('vx: freeSlotsRemainingA', this.freeSlotsRemainingA)
          if (response.data.subscribed_zip_codes_new !== null) {
            this.ownedSlotsA = response.data.subscribed_zip_codes_new.agent
            this.ownedSlotsL = response.data.subscribed_zip_codes_new.lender
            this.ownedSlotsGC = response.data.subscribed_zip_codes_new.gc
            // this.ownedSlots = response.data.subscribed_zip_codes    // vx: dragonite
          }
          this.loading = false
          //   this.fetchButtonSubscriptionStatus()
          //   vx: fetching slot subscription status
          // vx: uncomment!!
          axios.get('https://api.honely.com/lookup-test/fetch-subscription-zipcode?email=' + this.cognitoUser.attributes.email)
            .then((response) => {
              // vx: construct object for slot subscription list and for removal from freeTrialpriceIdList and nonFreeTrialpriceIdList
              // vx: don't forget below part!
              for (let x = 0; x < response.data.length; x++) {
                if (response.data[x].subtype === 1) {
                  var slotSubscriptionDetailA = {}
                  slotSubscriptionDetailA.start_date = response.data[x].start
                  slotSubscriptionDetailA.end_date = response.data[x].end
                  slotSubscriptionDetailA.renews = response.data[x].renews
                  slotSubscriptionDetailA.zipcode = response.data[x].zipcode // vx: check neten's object!!!!!
                  slotSubscriptionDetailA.subscription_id = response.data[x].subscription_id
                  slotSubscriptionDetailA.priceID = response.data[x].price_id
                  this.slotSubscriptionDetailsA.push(slotSubscriptionDetailA)
                }
                if (response.data[x].subtype === 2) {
                  var slotSubscriptionDetailL = {}
                  slotSubscriptionDetailL.start_date = response.data[x].start
                  slotSubscriptionDetailL.end_date = response.data[x].end
                  slotSubscriptionDetailL.renews = response.data[x].renews
                  slotSubscriptionDetailL.zipcode = response.data[x].zipcode // vx: check neten's object!!!!!
                  slotSubscriptionDetailL.subscription_id = response.data[x].subscription_id
                  slotSubscriptionDetailL.priceID = response.data[x].price_id
                  this.slotSubscriptionDetailsL.push(slotSubscriptionDetailL)
                }
                if (response.data[x].subtype === 3) {
                  var slotSubscriptionDetailGC = {}
                  slotSubscriptionDetailGC.start_date = response.data[x].start
                  slotSubscriptionDetailGC.end_date = response.data[x].end
                  slotSubscriptionDetailGC.renews = response.data[x].renews
                  slotSubscriptionDetailGC.zipcode = response.data[x].zipcode // vx: check neten's object!!!!!
                  slotSubscriptionDetailGC.subscription_id = response.data[x].subscription_id
                  slotSubscriptionDetailGC.priceID = response.data[x].price_id
                  this.slotSubscriptionDetailsGC.push(slotSubscriptionDetailGC)
                }
              }
              this.slotSubscriptionsLoaded = true
            })
            .catch(() => {
              this.loading = false
              this.slotSubscriptionsLoaded = true
            })
          // console.log('vx: pika this.slotSubscriptionDetails', this.slotSubscriptionDetails)
        })
        .catch(() => {
          this.loading = false
          this.slotSubscriptionsLoaded = true
        })
      /* axios.get('https://api.honely.com/lookup-test/fetch-subscription-leads?email=' + this.cognitoUser.attributes.email )
      axios.get('https://api.honely.com/lookup-test/agent_profile?agent_email=' + this.cognitoUser.attributes.email)
        .then((response) => {
          this.agent_id = response.data.agent_id
          axios.get('https://api.honely.com/lookup-test/fetch_leads?agent_id=' + this.agent_id)
            .then((response) => {
              this.site_leads = response.data.site_leads
              // for(let x = 0; x < this.site_leads.length; x++) {
              //   this.site_leads[x].first_name = this.site_leads[x].first_name.charAt(0).toUpperCase() + this.site_leads[x].first_name.slice(1)
              //   this.site_leads[x].last_name = this.site_leads[x].last_name.charAt(0).toUpperCase() + this.site_leads[x].last_name.slice(1)
              //   this.site_leads[x].phone_number = this.site_leads[x].phone_number.slice(0,3) + '-' + this.site_leads[x].phone_number.slice(3,6) + '-' + this.site_leads[x].phone_number.slice(6,10)
              // }
              this.button_leads = response.data.button_leads
              // for(let y = 0; y < this.button_leads.length; y++) {
              //   this.button_leads[y].first_name = this.button_leads[y].first_name.charAt(0).toUpperCase() + this.button_leads[y].first_name.slice(1)
              //   this.button_leads[y].last_name = this.button_leads[y].last_name.charAt(0).toUpperCase() + this.button_leads[y].last_name.slice(1)
              //   this.button_leads[y].phone_number = this.button_leads[y].phone_number.slice(0,3) + '-' + this.button_leads[y].phone_number.slice(3,6) + '-' + this.button_leads[y].phone_number.slice(6,10)
              // }
              this.loading = false
            })
        }) */
    },
    methods: {
      resetFetchSlotCount (userType) {
        const param = {
          searchLevel: 'zip',
          searchedValue: '',
          userType: userType,
        }
        this.fetchSlotCount(param)
      },
      zipPurchasePowerAvailable (userType) {
        if (userType === this.agent) {
          if ((this.zipPurchasePowerA - this.zipCartA.length) > 0) {
            return true
          }
        }
        if (userType === this.lender) {
          if ((this.zipPurchasePowerL - this.zipCartL.length) > 0) {
            return true
          }
        }
        if (userType === this.gc) {
          if ((this.zipPurchasePowerGC - this.zipCartGC.length) > 0) {
            return true
          }
        }
        return false
        // if ((this.zipPurchasePower - this.zipCart.length) > 0) {
        //   return true
        // } else {
        //   return false
        // }
      },
      updateSubscriptionDate (dateStr) {
        if (dateStr) {
          return new Date(dateStr).toDateString()
        } else {
          return ''
        }
      },
      fetchLeadsSlotsStatus () {
      },
      doSlotFreeTrialSubscription (value, userType) {
        if (!this.zipPurchasePowerAvailable(userType)) {
          if (userType === this.agent && this.zipCartA.length === 0) {
            bus.$emit('showError', { message: 'Sorry, you can only have a maximum of 5 active zipcode subscriptions!' })
          }
          if (userType === this.lender && this.zipCartL.length === 0) {
            bus.$emit('showError', { message: 'Sorry, you can only have a maximum of 5 active zipcode subscriptions!' })
          }
          if (userType === this.gc && this.zipCartGC.length === 0) {
            bus.$emit('showError', { message: 'Sorry, you can only have a maximum of 5 active zipcode subscriptions!' })
          }
        } else if (userType === this.agent && this.zipCartA.length === 5) {
          bus.$emit('showError', { message: 'Cannot subscribe to more than 5 zipcodes! Please remove existing zipcodes from your cart to add new ones.' })
        } else if (userType === this.lender && this.zipCartL.length === 5) {
          bus.$emit('showError', { message: 'Cannot subscribe to more than 5 zipcodes! Please remove existing zipcodes from your cart to add new ones.' })
        } else if (userType === this.gc && this.zipCartGC.length === 5) {
          bus.$emit('showError', { message: 'Cannot subscribe to more than 5 zipcodes! Please remove existing zipcodes from your cart to add new ones.' })
        } else if (!this.zipPurchasePowerAvailable(userType)) {
          bus.$emit('showError', { message: 'You can only subscribe to ' + this.zipPurchasePower + ' more zipcode(s). Please remove existing zipcodes from your cart to add new ones.' })
        } else {
          axios.get('https://api.honely.com/lookup-test/slot_checker?zip_code=' + value + '&user_type=' + userType)
            .then((response) => {
              if (response.data.status) {
                if (userType === this.agent) {
                  this.zipCartA.push(value)
                }
                if (userType === this.lender) {
                  this.zipCartL.push(value)
                }
                if (userType === this.gc) {
                  this.zipCartGC.push(value)
                }
                this.doSlotsPayment(userType)
                // var servicesLists = []
                // var interestedZipCodes = []
                // servicesLists.push(this.services_list)
                // interestedZipCodes.push(value)
                // const params = {
                //   agent_id: this.agent_id,
                //   business_address: '1150 East Palmetto Park Road Suite 800B Boca Raton FL 33487',
                //   business_name: this.agent_name,
                //   services_lists: servicesLists,
                //   interested_zip_codes: interestedZipCodes,
                //   agent_email: this.agent_email,
                // }
                // axios.post('https://api.honely.com/lookup-test/generate_license_agreement', params)
                //   .then(() => {
                //     this.zipCart.push(value)
                //     this.doSlotsPayment()
                //   })
              } else {
                this.refreshNeeded = true
                bus.$emit('showError', { message: 'Sorry, This zipcode is no longer available. Please refresh your page and check for available zipcodes.' })
              }
            })
        }
      },
      doSlotsPayment (userType) { // vx: dratini
        /*
        vx:
        updateValuesAndValidateBeforeSlotsCheckOut()
        freeTrialpriceIdList
        nonFreeTrialpriceIdList
        call fetch subscriptions and get list of active price ids
        remove the price ids from above 2 lists
        // x = how many free trial subscriptions this guy's currently active?
        x2 = how many more free trials this guy can use?
        // y = how many non free trial subscription this guy's currently active?
        freeindex = 0
        nonfreeindex = 0
        this.lineItems = []
        var lineItem = {}
        for each zipcode:
            is x2 > 0?
            if yes:
                lineItem = {
                    price: freeTrialpriceIdList[freeindex],
                    quantity: 1,
                }
                this.lineItems.push(slotsLineItem)
                x2--
                freeindex++
                // x++
            else:
                slotsLineItem = {
                    price: nonFreeTrialpriceIdList[nonfreeindex],
                    quantity: 1,
                }
                this.lineItems.push(slotsLineItem)
                nonfreeindex++
            //    y++
        temp table with agent-zipcode-freetrialcount-priceids-timestamp mapping
        checkout!
        what happens after subscription process?:
        update the number of free trials used up by the guy
        map stripe priceid to the customer's subscription of zip slot
        call rohan's subscription api
        clear temp table
        */
        var freeTrialpriceIdList = null
        var nonFreeTrialpriceIdList = null
        if (userType === this.agent) {
          // if (this.cognitoUser.attributes.email === 'giovannymirabal@yahoo.com') {
          //   freeTrialpriceIdList = ['price_1KR6lsKnYAv7HjWUcQqJcehQ', 'price_1KR6p5KnYAv7HjWUKo0eBvty', 'price_1KR6qKKnYAv7HjWUWyJ45gib', 'price_1KR6qvKnYAv7HjWUj6h7hpzh', 'price_1KR6rgKnYAv7HjWUZNFTBU0c']
          // } else {
          //   freeTrialpriceIdList = ['price_1KEb0qKnYAv7HjWUUcFF2WN2', 'price_1KEb14KnYAv7HjWUfqeZg0LT', 'price_1KEb1DKnYAv7HjWU3669Q8pV', 'price_1KEb1JKnYAv7HjWUMDndTEfi', 'price_1KEb1OKnYAv7HjWUlKxIynch'] // vx: PRODUCTION values
          // }
          freeTrialpriceIdList = ['price_1KafCnKnYAv7HjWUZFIAmARb', 'price_1KafDDKnYAv7HjWUFgb4EL7D', 'price_1KafDZKnYAv7HjWUTEc6r43U', 'price_1KafDuKnYAv7HjWUGbmGrnhu', 'price_1KafEIKnYAv7HjWUhJmDQCle']
          nonFreeTrialpriceIdList = ['price_1KEb1VKnYAv7HjWUaxZFcl66', 'price_1KEb1aKnYAv7HjWU40T7n46N', 'price_1KEb1gKnYAv7HjWUZjza4Xzo', 'price_1KEb1lKnYAv7HjWUJQvVOKqM', 'price_1KEb1qKnYAv7HjWUas5KYpUz'] // vx: production keys
        }
        if (userType === this.lender) {
          // if (this.cognitoUser.attributes.email === 'giovannymirabal@yahoo.com') {
          //   freeTrialpriceIdList = ['price_1KEb0qKnYAv7HjWUUcFF2WN2', 'price_1KEb14KnYAv7HjWUfqeZg0LT', 'price_1KEb1DKnYAv7HjWU3669Q8pV', 'price_1KEb1JKnYAv7HjWUMDndTEfi', 'price_1KEb1OKnYAv7HjWUlKxIynch'] // vx: PRODUCTION values
          // } else {
          //   freeTrialpriceIdList = ['price_1KR6lsKnYAv7HjWUcQqJcehQ', 'price_1KR6p5KnYAv7HjWUKo0eBvty', 'price_1KR6qKKnYAv7HjWUWyJ45gib', 'price_1KR6qvKnYAv7HjWUj6h7hpzh', 'price_1KR6rgKnYAv7HjWUZNFTBU0c']
          // }
          freeTrialpriceIdList = ['price_1KafEzKnYAv7HjWUpaUuIZM4', 'price_1KafFOKnYAv7HjWUe1v0MoWC', 'price_1KafG8KnYAv7HjWU7YXi1qId', 'price_1KafGmKnYAv7HjWUHhrDJZyI', 'price_1KafHFKnYAv7HjWUsZ4mmOpu']
          nonFreeTrialpriceIdList = ['price_1KR6sGKnYAv7HjWUBpqivnRu', 'price_1KR6snKnYAv7HjWUvrRiCkdK', 'price_1KR6tBKnYAv7HjWUYn9ILTok', 'price_1KR6tsKnYAv7HjWU8LAQizht', 'price_1KR6uWKnYAv7HjWUD1odUUZy']
        }
        if (userType === this.gc) {
          // freeTrialpriceIdList = ['price_1KR6vIKnYAv7HjWUZ0GHJeEZ', 'price_1KR6w0KnYAv7HjWUaDkZCGdY', 'price_1KR6weKnYAv7HjWUTq27KZ8n', 'price_1KR6y3KnYAv7HjWUIzFyKZ3d', 'price_1KR6ypKnYAv7HjWUZ0gfDAxP']
          freeTrialpriceIdList = ['price_1KafHdKnYAv7HjWUMF355Dov', 'price_1KafHxKnYAv7HjWUM62smvmW', 'price_1KafIkKnYAv7HjWUEgZtw2fh', 'price_1KafJVKnYAv7HjWUaM3vka8L', 'price_1KafJsKnYAv7HjWU8066cWqE']
          nonFreeTrialpriceIdList = ['price_1KR6z8KnYAv7HjWUrdV0Gt7z', 'price_1KR6zRKnYAv7HjWUd1wjrJMH', 'price_1KR70LKnYAv7HjWUQi5tpxSs', 'price_1KR70nKnYAv7HjWUzWRus4Tf', 'price_1KR71AKnYAv7HjWUlMKAZwhs']
        }
        // var freeTrialpriceIdList = ['price_1KEb8xKnYAv7HjWU8leFBN25', 'price_1KEb8xKnYAv7HjWU8leFBN25', 'price_1KEb8xKnYAv7HjWU8leFBN25', 'price_1KEb8xKnYAv7HjWU8leFBN25', 'price_1KEb8xKnYAv7HjWU8leFBN25'] // vx: one dollar product
        /*
        this call to be made in mounted.. do the freeTrialpriceIdList and nonFreeTrialpriceIdList elements removal part here.. (done below, look!)
        axios.get('https://api.honely.com/lookup-test/fetch_slots_subscriptoins?email=' + this.cognitoUser.attributes.email)
        .then((response) => {
            for(let x = 0; x< response.data.length; x++) {
                if(freeTrialpriceIdList.includes(response.data[x].data.priceID)) {
                    freeTrialpriceIdList.splice(freeTrialpriceIdList.indexOf(response.data[x].data.priceID), 1)
                }
                if(nonFreeTrialpriceIdList.includes(response.data[x].data.priceID)) {
                    nonFreeTrialpriceIdList.splice(nonFreeTrialpriceIdList.indexOf(response.data[x].data.priceID), 1)
                }
            }
        })
        */
        if (userType === this.agent) {
          for (let x = 0; x < this.slotSubscriptionDetailsA.length; x++) {
            if (freeTrialpriceIdList.includes(this.slotSubscriptionDetailsA[x].priceID)) { // vx: check neten's object and coorect this entire for loop..
              freeTrialpriceIdList.splice(freeTrialpriceIdList.indexOf(this.slotSubscriptionDetailsA[x].priceID), 1)
            }
            if (nonFreeTrialpriceIdList.includes(this.slotSubscriptionDetailsA[x].priceID)) {
              nonFreeTrialpriceIdList.splice(nonFreeTrialpriceIdList.indexOf(this.slotSubscriptionDetailsA[x].priceID), 1)
            }
          }
        }
        if (userType === this.lender) {
          for (let x = 0; x < this.slotSubscriptionDetailsL.length; x++) {
            if (freeTrialpriceIdList.includes(this.slotSubscriptionDetailsL[x].priceID)) { // vx: check neten's object and coorect this entire for loop..
              freeTrialpriceIdList.splice(freeTrialpriceIdList.indexOf(this.slotSubscriptionDetailsL[x].priceID), 1)
            }
            if (nonFreeTrialpriceIdList.includes(this.slotSubscriptionDetailsL[x].priceID)) {
              nonFreeTrialpriceIdList.splice(nonFreeTrialpriceIdList.indexOf(this.slotSubscriptionDetailsL[x].priceID), 1)
            }
          }
        }
        if (userType === this.gc) {
          for (let x = 0; x < this.slotSubscriptionDetailsGC.length; x++) {
            if (freeTrialpriceIdList.includes(this.slotSubscriptionDetailsGC[x].priceID)) { // vx: check neten's object and coorect this entire for loop..
              freeTrialpriceIdList.splice(freeTrialpriceIdList.indexOf(this.slotSubscriptionDetailsGC[x].priceID), 1)
            }
            if (nonFreeTrialpriceIdList.includes(this.slotSubscriptionDetailsGC[x].priceID)) {
              nonFreeTrialpriceIdList.splice(nonFreeTrialpriceIdList.indexOf(this.slotSubscriptionDetailsGC[x].priceID), 1)
            }
          }
        }
        // for (let x = 0; x < this.slotSubscriptionDetails.length; x++) {
        //   if (freeTrialpriceIdList.includes(this.slotSubscriptionDetails[x].priceID)) { // vx: check neten's object and coorect this entire for loop..
        //     freeTrialpriceIdList.splice(freeTrialpriceIdList.indexOf(this.slotSubscriptionDetails[x].priceID), 1)
        //   }
        //   if (nonFreeTrialpriceIdList.includes(this.slotSubscriptionDetails[x].priceID)) {
        //     nonFreeTrialpriceIdList.splice(nonFreeTrialpriceIdList.indexOf(this.slotSubscriptionDetails[x].priceID), 1)
        //   }
        // }
        var x2 = 0
        if (userType === this.agent) {
          x2 = this.freeSlotsRemainingA
        }
        if (userType === this.lender) {
          x2 = this.freeSlotsRemainingL
        }
        if (userType === this.gc) {
          x2 = this.freeSlotsRemainingGC
        }
        // console.log('vx: x2 is (needs to be 5)', x2)
        var freeindex = 0
        var nonfreeindex = 0
        this.lineItems = []
        var freeOrPaid = ''
        var tempTableInsertionBody = {
          type: 'TEMP',
          agent_id: this.agent_id,
          agent_email: this.cognitoUser.attributes.email,
          user_type: this.userType,
          zip_code: '',
          price_id: '',
          subscription_type: '',
        }
        if (userType === this.agent) {
          for (let x = 0; x < this.zipCartA.length; x++) {
            var lineItem = {}
            if (x2 > 0) {
              lineItem.price = freeTrialpriceIdList[freeindex]
              lineItem.quantity = 1
              this.lineItems.push(lineItem)
              freeOrPaid = 'FREE'
              // build tempTableInsertionBody
              if (x === 0) {
                tempTableInsertionBody.zip_code = this.zipCartA[x]
                tempTableInsertionBody.price_id = lineItem.price
                tempTableInsertionBody.subscription_type = 'FREE'
              } else {
                tempTableInsertionBody.zip_code = tempTableInsertionBody.zip_code + '|' + this.zipCartA[x]
                tempTableInsertionBody.price_id = tempTableInsertionBody.price_id + '|' + lineItem.price
                tempTableInsertionBody.subscription_type = tempTableInsertionBody.subscription_type + '|' + 'FREE'
              }
              x2--
              freeindex++
            } else {
              lineItem.price = nonFreeTrialpriceIdList[nonfreeindex]
              lineItem.quantity = 1
              this.lineItems.push(lineItem)
              freeOrPaid = 'PAID'
              // build tempTableInsertionBody
              if (x === 0) {
                tempTableInsertionBody.zip_code = this.zipCartA[x]
                tempTableInsertionBody.price_id = lineItem.price
                tempTableInsertionBody.subscription_type = 'PAID'
              } else {
                tempTableInsertionBody.zip_code = tempTableInsertionBody.zip_code + '|' + this.zipCartA[x]
                tempTableInsertionBody.price_id = tempTableInsertionBody.price_id + '|' + lineItem.price
                tempTableInsertionBody.subscription_type = tempTableInsertionBody.subscription_type + '|' + 'PAID'
              }
              nonfreeindex++
            }
          }
        }
        if (userType === this.lender) {
          for (let x = 0; x < this.zipCartL.length; x++) {
            lineItem = {}
            if (x2 > 0) {
              lineItem.price = freeTrialpriceIdList[freeindex]
              lineItem.quantity = 1
              this.lineItems.push(lineItem)
              freeOrPaid = 'FREE'
              // build tempTableInsertionBody
              if (x === 0) {
                tempTableInsertionBody.zip_code = this.zipCartL[x]
                tempTableInsertionBody.price_id = lineItem.price
                tempTableInsertionBody.subscription_type = 'FREE'
              } else {
                tempTableInsertionBody.zip_code = tempTableInsertionBody.zip_code + '|' + this.zipCartL[x]
                tempTableInsertionBody.price_id = tempTableInsertionBody.price_id + '|' + lineItem.price
                tempTableInsertionBody.subscription_type = tempTableInsertionBody.subscription_type + '|' + 'FREE'
              }
              x2--
              freeindex++
            } else {
              lineItem.price = nonFreeTrialpriceIdList[nonfreeindex]
              lineItem.quantity = 1
              this.lineItems.push(lineItem)
              freeOrPaid = 'PAID'
              // build tempTableInsertionBody
              if (x === 0) {
                tempTableInsertionBody.zip_code = this.zipCartL[x]
                tempTableInsertionBody.price_id = lineItem.price
                tempTableInsertionBody.subscription_type = 'PAID'
              } else {
                tempTableInsertionBody.zip_code = tempTableInsertionBody.zip_code + '|' + this.zipCartL[x]
                tempTableInsertionBody.price_id = tempTableInsertionBody.price_id + '|' + lineItem.price
                tempTableInsertionBody.subscription_type = tempTableInsertionBody.subscription_type + '|' + 'PAID'
              }
              nonfreeindex++
            }
          }
        }
        if (userType === this.gc) {
          for (let x = 0; x < this.zipCartGC.length; x++) {
            lineItem = {}
            if (x2 > 0) {
              lineItem.price = freeTrialpriceIdList[freeindex]
              lineItem.quantity = 1
              this.lineItems.push(lineItem)
              freeOrPaid = 'FREE'
              // build tempTableInsertionBody
              if (x === 0) {
                tempTableInsertionBody.zip_code = this.zipCartGC[x]
                tempTableInsertionBody.price_id = lineItem.price
                tempTableInsertionBody.subscription_type = 'FREE'
              } else {
                tempTableInsertionBody.zip_code = tempTableInsertionBody.zip_code + '|' + this.zipCartGC[x]
                tempTableInsertionBody.price_id = tempTableInsertionBody.price_id + '|' + lineItem.price
                tempTableInsertionBody.subscription_type = tempTableInsertionBody.subscription_type + '|' + 'FREE'
              }
              x2--
              freeindex++
            } else {
              lineItem.price = nonFreeTrialpriceIdList[nonfreeindex]
              lineItem.quantity = 1
              this.lineItems.push(lineItem)
              freeOrPaid = 'PAID'
              // build tempTableInsertionBody
              if (x === 0) {
                tempTableInsertionBody.zip_code = this.zipCartGC[x]
                tempTableInsertionBody.price_id = lineItem.price
                tempTableInsertionBody.subscription_type = 'PAID'
              } else {
                tempTableInsertionBody.zip_code = tempTableInsertionBody.zip_code + '|' + this.zipCartGC[x]
                tempTableInsertionBody.price_id = tempTableInsertionBody.price_id + '|' + lineItem.price
                tempTableInsertionBody.subscription_type = tempTableInsertionBody.subscription_type + '|' + 'PAID'
              }
              nonfreeindex++
            }
          }
        }
        // vx: testing stuff, remove..
        // this.lineItems = [
        //     {
        //         price: 'price_1KCA3cKnYAv7HjWUX2R56nsV',
        //         quantity: 1,
        //     },
        //     {
        //         price: 'price_1KCA3FKnYAv7HjWUqSOnlqjz',
        //         quantity: 1,
        //     },
        // ]
        // var n = 0
        if (userType === this.agent) {
          //   n = 1
          this.successURL = 'https://www.honely.com/slotPaymentSuccess?agentId=' + this.agent_id + '&priceID=' + this.lineItems[0].price + '&agentType=' + userType + '&zipCode=' + this.zipCartA[0] + '&subscriptionType=' + freeOrPaid + '&services_list=' + this.services_list + '&interested_zip_codes=' + this.zipCartA[0] + '&agent_email=' + this.agent_email + '&purchasePower=' + this.zipPurchasePowerA + '&first_name=' + this.agent_first_name + '&last_name=' + this.agent_last_name
        }
        if (userType === this.lender) {
          //   n = 2
          this.successURL = 'https://www.honely.com/slotPaymentSuccess?agentId=' + this.agent_id + '&priceID=' + this.lineItems[0].price + '&agentType=' + userType + '&zipCode=' + this.zipCartL[0] + '&subscriptionType=' + freeOrPaid + '&services_list=' + this.services_list + '&interested_zip_codes=' + this.zipCartL[0] + '&agent_email=' + this.agent_email + '&purchasePower=' + this.zipPurchasePowerL + '&first_name=' + this.agent_first_name + '&last_name=' + this.agent_last_name
        }
        if (userType === this.gc) {
          //   n = 3
          this.successURL = 'https://www.honely.com/slotPaymentSuccess?agentId=' + this.agent_id + '&priceID=' + this.lineItems[0].price + '&agentType=' + userType + '&zipCode=' + this.zipCartGC[0] + '&subscriptionType=' + freeOrPaid + '&services_list=' + this.services_list + '&interested_zip_codes=' + this.zipCartGC[0] + '&agent_email=' + this.agent_email + '&purchasePower=' + this.zipPurchasePowerGC + '&first_name=' + this.agent_first_name + '&last_name=' + this.agent_last_name
        }
        console.log('vx: this.successURL', this.successURL) // vx: dragonite
        // console.log('vx: lineItems', this.lineItems)
        // temp table with agent-zipcode-freetrialcount-priceids-timestamp mapping
        console.log('vx: this.lineItems', this.lineItems)
        try {
          this.$refs.checkoutRef.redirectToCheckout()
        //   const errorObj = {
        //     message: 'lalala',
        //   }
        //   throw errorObj
        } catch (error) {
          this.refreshNeeded = true
          bus.$emit('showError', { message: 'Sorry, something went wrong. Please refresh the page and try again.' })
        }
        // axios.post('https://api.honely.com/lookup-test/leads_subscription_tracker', tempTableInsertionBody)
        //   .then(() => {
        //     this.$refs.checkoutRef.redirectToCheckout()
        //   })
        //   .catch(() => {
        //     console.log('vx: error in slots checkout!!')
        //     this.displayErrorSlotCheckoutMsg()
        //   })
      },
      updateValuesAndValidateBeforeSlotsCheckOut () {
        /*
        vx:
        get updated values..
        what are the values?
        ????
        if the no of items in cart > the number of slots he can purchase:
            complain and abort checkout!
        */
      },
      isInOwnedSlots (value, userType) {
        // vx: dragonite
        if (userType === this.agent && this.ownedSlotsA.includes(value)) {
          return true
        }
        if (userType === this.lender && this.ownedSlotsL.includes(value)) {
          return true
        }
        if (userType === this.gc && this.ownedSlotsGC.includes(value)) {
          return true
        }
        return false
        // if (this.ownedSlots.includes(value)) {
        //   return true
        // } else {
        //   return false
        // }
      },
      fetchSlotCount (x) {
        if (x.searchLevel === 'zip') {
          this.leadSlotSearchInput = x.searchedValue
          this.fetchZipSlotCount(x.userType)
        } else if (x.searchLevel === 'city') {
          console.log('vx: city level search..')
          this.leadSlotSearchInput = x.searchedValue
          this.fetchCitySlotCount(x.userType)
        } else {
          this.displayErrorSearchMsg()
        }
      },
      displayErrorSlotCheckoutMsg () {
        //   vx: todo
      },
      fetchCitySlotCount (userType) {
        var lala = this.leadSlotSearchInput.split(', ')
        const x = {
          params: {
            city: lala[0],
            state: lala[1],
          },
        }
        axios.get('https://api.honely.com/lookup-test/city_to_zip', x)
          .then((response) => {
            this.leadSlotSearchInput = response.data[0]
            for (var z = 1; z < response.data.length; z++) {
              this.leadSlotSearchInput = this.leadSlotSearchInput + "','" + response.data[z]
            }
            this.fetchZipSlotCount(userType)
          })
      },
      displayErrorSearchMsg () {
        //   vx: todo
      },
      fetchZipSlotCount (userType) {
        const params = {
          user_type: userType,
          zip_code: "('" + this.leadSlotSearchInput + "')",
        }
        this.zipSlotRecordsA = []
        this.zipSlotRecordsL = []
        this.zipSlotRecordsGC = []
        axios.post('https://api.honely.com/lookup-test/zip_code_subscription_count', params)
          .then((response) => {
            console.log('vx: lalala', response.data)
            if (userType === this.agent) {
              for (let i = 0; i < response.data.length; i++) {
                var x = {}
                x.zipcode = response.data[i].zip_code
                x.slotsAvailable = 3 - parseInt(response.data[i].subscription_count)
                this.zipSlotRecordsA.push(x)
              }
            }
            if (userType === this.lender) {
              for (let i = 0; i < response.data.length; i++) {
                x = {}
                x.zipcode = response.data[i].zip_code
                x.slotsAvailable = 3 - parseInt(response.data[i].subscription_count)
                this.zipSlotRecordsL.push(x)
              }
            }
            if (userType === this.gc) {
              for (let i = 0; i < response.data.length; i++) {
                x = {}
                x.zipcode = response.data[i].zip_code
                x.slotsAvailable = 3 - parseInt(response.data[i].subscription_count)
                this.zipSlotRecordsGC.push(x)
              }
            }
            // for (let i = 0; i < response.data.length; i++) {
            //   var x = {}
            //   x.zipcode = response.data[i].zip_code
            //   x.slotsAvailable = 3 - parseInt(response.data[i].subscription_count)
            //   this.zipSlotRecords.push(x)
            // }
          })
        // console.log('vx: zipSlotRecords is', this.zipSlotRecords)
      },
      isZipInCart (zipcode, userType) {
        if (userType === this.agent) {
          if (this.zipCartA.indexOf(zipcode) === -1) {
            return false
          }
        }
        if (userType === this.lender) {
          if (this.zipCartL.indexOf(zipcode) === -1) {
            return false
          }
        }
        if (userType === this.gc) {
          if (this.zipCartGC.indexOf(zipcode) === -1) {
            return false
          }
        }
        return true
        // if (this.zipCart.indexOf(zipcode) === -1) {
        //   return false
        // } else {
        //   return true
        // }
      },
      addZipToCart (zipcode, userType) {
        console.log('vx: entering addZipToCart......')
        if (!this.zipPurchasePowerAvailable(userType)) {
          if (userType === this.agent && this.zipCartA.length === 0) {
            bus.$emit('showError', { message: 'Sorry, you can only have a maximum of 5 active zipcode subscriptions!' })
          }
          if (userType === this.lender && this.zipCartL.length === 0) {
            bus.$emit('showError', { message: 'Sorry, you can only have a maximum of 5 active zipcode subscriptions!' })
          }
          if (userType === this.gc && this.zipCartGC.length === 0) {
            bus.$emit('showError', { message: 'Sorry, you can only have a maximum of 5 active zipcode subscriptions!' })
          }
        } else if (userType === this.agent && this.zipCartA.length === 5) {
          bus.$emit('showError', { message: 'Cannot subscribe to more than 5 zipcodes! Please remove existing zipcodes from your cart to add new ones.' })
        } else if (userType === this.lender && this.zipCartL.length === 5) {
          bus.$emit('showError', { message: 'Cannot subscribe to more than 5 zipcodes! Please remove existing zipcodes from your cart to add new ones.' })
        } else if (userType === this.gc && this.zipCartGC.length === 5) {
          bus.$emit('showError', { message: 'Cannot subscribe to more than 5 zipcodes! Please remove existing zipcodes from your cart to add new ones.' })
        } else if (!this.zipPurchasePowerAvailable(userType)) {
          bus.$emit('showError', { message: 'You can only subscribe to ' + this.zipPurchasePower + ' more zipcode(s). Please remove existing zipcodes from your cart to add new ones.' })
        } else {
          // vx: slot availability check here..
          axios.get('https://api.honely.com/lookup-test/slot_checker?zip_code=' + zipcode + '&user_type=' + userType)
            .then((response) => {
              if (response.data.status) {
                if (userType === this.agent) {
                  this.zipCartA.push(zipcode)
                }
                if (userType === this.lender) {
                  this.zipCartL.push(zipcode)
                }
                if (userType === this.gc) {
                  this.zipCartGC.push(zipcode)
                }
                this.doSlotsPayment(userType)
                // var servicesLists = []
                // var interestedZipCodes = []
                // servicesLists.push(this.services_list)
                // interestedZipCodes.push(zipcode)
                // const params = {
                //   agent_id: this.agent_id,
                //   business_address: '1150 East Palmetto Park Road Suite 800B Boca Raton FL 33487',
                //   business_name: this.agent_name,
                //   services_lists: servicesLists,
                //   interested_zip_codes: interestedZipCodes,
                //   agent_email: this.agent_email,
                // }
                // axios.post('https://www.api.honely.com/lookup-test/generate_license_agreement', params)
                //   .then(() => {
                //     this.zipCart.push(zipcode)
                //     this.doSlotsPayment()
                //   })
              } else {
                this.refreshNeeded = true
                bus.$emit('showError', { message: 'Sorry, This zipcode is no longer available. Please refresh your page and check for available zipcodes.' })
              }
            })
        }
      },
      removeZipFromCart (zipcode) {
        this.zipCart.splice(this.zipCart.indexOf(zipcode), 1)
      },
      /* fetchLeadsList () {
        axios.get('https://api.honely.com/lookup-test/fetch_leads?agent_id=' + this.agent_id)
          .then((response) => {
            this.site_leads = response.data.site_leads
            // for(let x = 0; x < this.site_leads.length; x++) {
            //   this.site_leads[x].first_name = this.site_leads[x].first_name.charAt(0).toUpperCase() + this.site_leads[x].first_name.slice(1)
            //   this.site_leads[x].last_name = this.site_leads[x].last_name.charAt(0).toUpperCase() + this.site_leads[x].last_name.slice(1)
            //   this.site_leads[x].phone_number = this.site_leads[x].phone_number.slice(0,3) + '-' + this.site_leads[x].phone_number.slice(3,6) + '-' + this.site_leads[x].phone_number.slice(6,10)
            // }
            this.button_leads = response.data.button_leads
            // for(let y = 0; y < this.button_leads.length; y++) {
            //   this.button_leads[y].first_name = this.button_leads[y].first_name.charAt(0).toUpperCase() + this.button_leads[y].first_name.slice(1)
            //   this.button_leads[y].last_name = this.button_leads[y].last_name.charAt(0).toUpperCase() + this.button_leads[y].last_name.slice(1)
            //   this.button_leads[y].phone_number = this.button_leads[y].phone_number.slice(0,3) + '-' + this.button_leads[y].phone_number.slice(3,6) + '-' + this.button_leads[y].phone_number.slice(6,10)
            // }
          })
      }, */
      fetchButtonSubscriptionStatus () {
        // vx: set loading true after reposition loading element
        axios.get('https://api.honely.com/lookup-test/fetch-subscription-leads?email=' + this.cognitoUser.attributes.email)
          .then((response) => {
            this.loading = false
            // console.log('')
            if (response.data.data.customer_id === null) {
              this.showButtonSubscribeSection = true
            } else {
              this.showButtonSubscriptionDetails = true
              this.btnSubscriptionStartDate = response.data.data.start_date.slice(0, -12)
              if (response.data.data.renews === 0) {
                this.btnSubscriptionEndDate = response.data.data.end_date.slice(0, -12)
              }
              if (response.data.data.renews !== 0) {
                this.btnSubscriptionRenewalDate = response.data.data.end_date.slice(0, -12)
              }
              this.btnSubscriptionId = response.data.data.subscription_id
              // this.trialUsed = response.data.trialUsed
              if (response.data.data.renews !== 0) {
                this.showButtonUnsubscribeBtn = true
              }
            }
          })
          .catch(() => {
            this.loading = false
          })
      },
      cancelButtonSubscription () {
        axios.get('https://api.honely.com/lookup-test/cancel-subscription-leads?id=' + this.btnSubscriptionId)
          .then(() => {
            location.reload()
          })
      },
      cancelSlotSubscription (subscriptionID, agentId, zipCode, userType) {
        axios.get('https://api.honely.com/lookup-test/cancel-subscription-zipcode?id=' + subscriptionID)
          .then(() => {
            // var userTypeParam = null
            // if (userType === 'AGENT/BROKER') {
            //   userTypeParam = 1
            // }
            // if (userType === 'LENDER') {
            //   userTypeParam = 2
            // }
            // if (userType === 'GENERAL CONTRACTOR') {
            //   userTypeParam = 3
            // }
            const params = {
              agent_id: agentId,
              zip_code: zipCode,
              user_type: userType,
            }
            const params2 = {
              agent_id: agentId,
              zip_code: zipCode,
              subtype: userType,
            }
            axios.post('https://api.honely.com/lookup-test/zip_code_unsubscribe', params)
              .then(() => {
                axios.post('https://api.honely.com/lookup-test/leads_subscription_remover', params2)
                  .then(() => {
                    location.reload()
                  })
              })
          })
      },
      gotoLinkSubscription () {
        this.tab = 'tab-1'
      },
      doPayment () {
        this.lineItems = [
          {
            price: 'price_1KEawhKnYAv7HjWUz7qr3ccf', // vx: no trial
            quantity: 1,
          },
        ]
        this.successURL = 'https://www.honely.com/paymentSuccess?agent_id=' + this.agent_id + '&business_address=' + this.enteredBusinessAddress + '&company_type=' + this.companyType + '&website_url=' + this.enteredHomeUrl + '&agent_email=' + this.agent_email + '&business_name=' + this.enteredBusinessName
        if (this.enteredBusinessName === null || this.enteredBusinessName === '') {
          this.urlPrompt = 'Please enter business name'
        } else if (this.enteredBusinessAddress === null || this.enteredBusinessAddress === '') {
          this.urlPrompt = 'Please enter business address'
        } else if (!this.isAuthorizedRep) {
          this.urlPrompt = 'Please confirm that you are an authorized representative'
        } else if (this.companyType === null) {
          this.urlPrompt = 'Please select the type of your company'
        } else if (this.enteredHomeUrl === null || this.enteredHomeUrl === '') {
          this.urlPrompt = 'Please paste the URL'
        } else {
          //   var companyType = []
          //   companyType.push(this.companyType)
          //   const contractParams = {
          //     agent_id: this.agent_id,
          //     business_address: this.enteredBusinessAddress,
          //     business_name: this.enteredBusinessName,
          //     company_type: companyType,
          //     website_url: this.enteredHomeUrl,
          //     agent_email: this.agent_email,
          //     authorized_flag: true,
          //   }
          //   axios.post('https://api.honely.com/lookup-test/generate_home_worth_agreement', contractParams)
          const params = {
            agent_id: this.agent_id,
            information: ['home_url', 'company_type', 'business_address'],
            home_url_value: this.enteredHomeUrl,
            company_type_value: this.companyType,
            business_address_value: this.enteredBusinessAddress,
          }
          axios.post('https://api.honely.com/lookup-test/agent_profile_modification', params)
            .then(() => {
              this.urlPrompt = ''
              this.$refs.checkoutRef.redirectToCheckout()
            })
            .catch(() => {
              this.urlPrompt = 'Error in subscribing. Please contact contact@honely.com'
            })
        }
      },
      generateCsv (type) {
        axios.get('https://api.honely.com/lookup-test/generate_leads_csv?&agent_id=' + this.agent_id + '&lead_type=' + type)
          .then(() => {
            // https://honely-leads-reports.s3.amazonaws.com/AGHT3476.csv
            window.location.href = 'https://honely-leads-reports.s3.amazonaws.com/' + this.agent_id + '.csv'
          })
      },
    },
  }
</script>
