export const REQUIREED_RULE = (errTxt) => v => !!v || (errTxt || 'This field is required')
export const ARRAY_RULES = (errTxt) => v => (Array.isArray(v) && v.length > 0) || (errTxt || 'This field is required')
export const REQUIRED_CHAR_NUMBER_RULE = (errTxt) => v => /^[A-Za-z0-9]*$/.test(v) || (errTxt || 'User name cannot contain spaces or special characters')
export const REQUIRED_CHAR_RULE = (errTxt) => v => /^[A-Za-z][A-Za-z\s]*$/.test(v) || (errTxt || 'User name cannot contain spaces or special characters')
export const LEN_LESS_RULE = (len, errTxt) => v => v.length >= len || (errTxt || `Min ${len} Characters`)
export const LEN_MORE_RULE = (len, errTxt) => v => v.length <= len || (errTxt || `Max ${len} Characters`)
export const REQUIRED_EMAIL_RULE = (errTxt) => v => /.+@.+\..+/.test(v) || (errTxt || 'E-mail must be valid')
export const REQUIRED_PHONE_RULE = (errTxt) => v => (!v || v.length === 10) || (errTxt || 'Phone number must contain 10 digits')
// export const REQUIRED_PASSWORD_LEN = (len, errTxt) => v => /(?=.{8,})/.test(v) || (errTxt || 'Password must contain at least 8 characters')
// export const REQUIRED_SPECIAL_CHAR = (errTxt) => v => /(?=.*[!@#$%^&*])/.test(v) || (errTxt || 'Password must contain at 1 speacial character')
// export const REQUIRED_ONLY_NUMBER = (errTxt) => v => /(?=.*[0-9])/.test(v) || (errTxt || 'Password should contain at least 1 number')
// export const REQUIRED_ONLY_FLOAT = (num) => /[+-]?([0-9]*[.])?[0-9]+/.test(num)
// export const REQUIRED_ONLY_UPPER = (errTxt) => v => /(?=.*[A-Z])/.test(v) || (errTxt || 'Password should contain at least 1 uppercase character')
// export const REQUIRED_ONLY_LOWER = (errTxt) => v => /(?=.*[a-z])/.test(v) || (errTxt || 'Password should contain at least 1 lowercase character')
export const REQUIRED_PASSWORD_LEN = (len, errTxt) => v => /(?=.{8,})/.test(v) || (errTxt || '')
export const REQUIRED_SPECIAL_CHAR = (errTxt) => v => /(?=.*[!@#$%^&*])/.test(v) || (errTxt || '')
export const REQUIRED_ONLY_NUMBER = (errTxt) => v => /(?=.*[0-9])/.test(v) || (errTxt || '')
export const REQUIRED_ONLY_FLOAT = (num) => /[+-]?([0-9]*[.])?[0-9]+/.test(num)
export const REQUIRED_ONLY_UPPER = (errTxt) => v => /(?=.*[A-Z])/.test(v) || (errTxt || '')
export const REQUIRED_ONLY_LOWER = (errTxt) => v => /(?=.*[a-z])/.test(v) || (errTxt || '')
