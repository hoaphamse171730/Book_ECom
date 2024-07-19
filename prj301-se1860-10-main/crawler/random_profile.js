const { fakerVI } = require('@faker-js/faker');

function generateApartmentNumber() {
    const hasSlash = Math.random() < 0.5;
    if (hasSlash) {
        const firstPart = Math.floor(Math.random() * 100);
        const secondPart = Math.floor(Math.random() * 100);
        return `${firstPart}/${secondPart}`;
    } else {
        return Math.floor(Math.random() * 100).toString();
    }
}

function removeVietnameseTones(str) {
    str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g,"a");
    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g,"e");
    str = str.replace(/ì|í|ị|ỉ|ĩ/g,"i");
    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g,"o");
    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g,"u");
    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g,"y");
    str = str.replace(/đ/g,"d");
    str = str.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
    str = str.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
    str = str.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
    str = str.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
    str = str.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
    str = str.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
    str = str.replace(/Đ/g, "D");
    // Some system encode vietnamese combining accent as individual utf-8 characters
    // Một vài bộ encode coi các dấu mũ, dấu chữ như một kí tự riêng biệt nên thêm hai dòng này
    str = str.replace(/\u0300|\u0301|\u0303|\u0309|\u0323/g, ""); // ̀ ́ ̃ ̉ ̣  huyền, sắc, ngã, hỏi, nặng
    str = str.replace(/\u02C6|\u0306|\u031B/g, ""); // ˆ ̆ ̛  Â, Ê, Ă, Ơ, Ư
    // Remove extra spaces
    // Bỏ các khoảng trắng liền nhau
    str = str.replace(/ + /g," ");
    str = str.trim();
    // Remove punctuations
    // Bỏ dấu câu, kí tự đặc biệt
    str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\"|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g," ");
    return str;
}

function transformToEmail(fullName) {
    fullName = removeVietnameseTones(fullName.replaceAll(' ', '').toLowerCase());
    const randomSuffix = Math.floor(Math.random() * 1000);
    const emailDomains = ['gmail.com', 'hotmail.com', 'yahoo.com'];
    const randomDomain = emailDomains[Math.floor(Math.random() * emailDomains.length)];
    return `${fullName}${randomSuffix}@${randomDomain}`;
}

let repeatCount = 1;

if (process.argv.length > 2) {
    const userProvidedCount = parseInt(process.argv[2]);
    if (!isNaN(userProvidedCount) && userProvidedCount > 0) {
        repeatCount = userProvidedCount;
    }
}

const arr = [];

for (let i = 0; i < repeatCount; i++) {
    const name = fakerVI.person.fullName();
    arr.push({
        name: name,
        gender: fakerVI.person.sexType(),
        email: transformToEmail(name),
        address: `${generateApartmentNumber()} ${fakerVI.person.firstName()}, ${fakerVI.location.city()}`,
        phone: fakerVI.phone.number().replaceAll(' ', ''),
        password: fakerVI.internet.password()
    })
}

console.log(JSON.stringify(arr))
