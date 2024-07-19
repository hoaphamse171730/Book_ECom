<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script>
    const plusBtn = document.getElementById("plus-btn");
    const minusBtn = document.getElementById("minus-btn");
    const quantityInput = document.getElementById("quantity-input");

    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    const minusButtons = document.querySelectorAll('div.minus-btn');
    const plusButtons = document.querySelectorAll('div.plus-btn');
    const nofiContainer = document.getElementById('noti-container');
    
    const showNotice = () => {
    	//nofiContainer.classList.toggle = "hidden";
    	nofiContainer.classList.toggle('hidden');
    }

    minusButtons.forEach(minusButton => {
        minusButton.onclick = (e) => {
            const id = e.target.id;
            const quantity = document.getElementById("quantity-input-" + e.target.id);
            const price = Number(document.querySelector("#p" + id + ".price").innerText.replace("đ", ""));
            const money = document.querySelector("#p" + id + ".productTotalMoney");
            const total = document.querySelector("#p" + id + ".productTotalMoney");

            const checkbox = document.getElementById('check' + id);
            if (Number(quantity.value) >= 2) {
                changeAmountItemToCookie(id, Number(quantity.value) - 1);
                total.innerText = price * (Number(quantity.value) - 1) + "đ";
                quantity.value = Number(quantity.value) - 1;
                if (checkbox.checked) {
                    const totalAmount = document.getElementById("totalAmount");
                    const totalMoney = document.getElementById("totalMoney");
                    totalAmount.innerText = Number(totalAmount.innerText) - 1;
                    totalMoney.innerText = Number(totalMoney.innerText.replace("đ", "")) - price + "đ";

                }
            }
        }
    });

    plusButtons.forEach(plusButton => {
        plusButton.onclick = (e) => {
            const id = e.target.id;
            const quantity = document.getElementById("quantity-input-" + e.target.id);
            console.log(quantity.value);
            const money = document.querySelector("#p" + id + ".productTotalMoney");
            const price = Number(document.querySelector("#p" + id + ".price").innerText.replace("đ", ""));
            const total = document.querySelector("#p" + id + ".productTotalMoney");

            changeAmountItemToCookie(id, Number(quantity.value) + 1);
            total.innerText = price * (Number(quantity.value) + 1) + "đ";
            quantity.value = Number(quantity.value) + 1;

            const checkbox = document.getElementById('check' + id);
            if (checkbox.checked) {
                const totalAmount = document.getElementById("totalAmount");
                const totalMoney = document.getElementById("totalMoney");
                totalAmount.innerText = Number(totalAmount.innerText) + 1;
                totalMoney.innerText = Number(totalMoney.innerText.replace("đ", "")) + price + "đ";
            }
        }
    });

    const changeAmountItemToCookie = (productId, quantity) => {
        let cookieResult = "";
        if (!getCookie("cart")) {
            cookieResult = productId + ":" + quantity + "/";
            setCookie("cart", cookieResult);
            return;
        }
        let cartItems = getCookie("cart").split("/");
        cartItems.pop();
        let flag = true;
        for (let item of cartItems) {
            if (item.split(":")[0] == productId) {
                flag = false;
                cookieResult += item.split(":")[0] + ":" + quantity + "/";
            } else {
                cookieResult += item + "/"
            }
        }
        if (flag) {
            cookieResult += productId + ":" + quantity + "/";
        }
        setCookie("cart", cookieResult);
    }

    const removeItemFromCookie = (productId) => {
        let cookieResult = "";
        let cartItems = getCookie("cart").split("/");
        cartItems.pop();
        for (let item of cartItems) {
            if (item.split(":")[0] != productId) {
                cookieResult += item + "/"
            }
        }
        setCookie("cart", cookieResult);
    }

    function openModal(productId) {
        const modal = document.getElementById('modal');
        const yesBtn = document.getElementById('modal-yes-btn');
        console.log(yesBtn);
        yesBtn.addEventListener('click', () => {
            removeItem(productId);
        })
        modal.classList.remove('hidden');
        modal.classList.add('fixed');
    }

    function removeItem(productId) {
        const itemToRemove = document.getElementById('parent-' + productId);
        if (itemToRemove) {
            itemToRemove.remove();
            removeItemFromCookie(productId)
        }
        closeModal()
    }

    function closeModal() {
        const modal = document.getElementById('modal');
        modal.classList.add('hidden')
        modal.classList.remove('fixed');
    }


    checkboxes.forEach(checkbox => {
        checkbox.onclick = (e) => {
            const id = (e.target.id).replace("check", "");
            const quantity = document.getElementById("quantity-input-" + id).value;
            const money = document.querySelector("#p" + id + ".productTotalMoney");
            const totalAmount = document.getElementById("totalAmount");
            const totalMoney = document.getElementById("totalMoney");
            if (e.target.checked) {
                totalAmount.innerText = Number(totalAmount.innerText) + Number(quantity);
                totalMoney.innerText = Number(totalMoney.innerText.replace("đ", "")) + Number(money.innerText.replace("đ", "")) + "đ";
            } else {
                totalAmount.innerText = Number(totalAmount.innerText) - Number(quantity);
                totalMoney.innerText = Number(totalMoney.innerText.replace("đ", "")) - Number(money.innerText.replace("đ", "")) + "đ";
            }
        }
    });

    if (minusBtn) {
        minusBtn.onclick = (e) => {
            if (Number(quantityInput.value) >= 2) {
                quantityInput.value = (Number(quantityInput.value)) - 1;
            }
        }
    }

    if (plusBtn) {
        plusBtn.onclick = (e) => {
            quantityInput.value = (Number(quantityInput.value)) + 1;
        }
    }

    function setCookie(name, value) {
        var expires = "";
        var date = new Date();
        date.setTime(date.getTime() + (365 * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
        document.cookie = name + "=" + (value || "") + expires + "; path=/";
    }

    function getCookie(name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1, c.length);
            }

            if (c.indexOf(nameEQ) == 0) {
                return c.substring(nameEQ.length, c.length);
            }
        }
        return null;
    }

    function toggleDropdown() {
        const dropdownMenu = document.getElementById('dropdown-menu');
        dropdownMenu.classList.toggle('hidden');
    }

    function handleItemClick(item) {
        alert(item + " clicked");
    }

    function addItemToCart(id) {
        window.location.href = "http://localhost:8080/shopcart?action=add&product="+id;
        alert("Thêm vào giỏ hàng thành công");
    }
    
    function showMessageBox() {
        var status = document.getElementById("status");
        var messageBox = document.getElementById("message");

        if (status.value === "CANCELLED") {
        	messageBox.style.display = "block";
        } else {
        	messageBox.style.display = "none";
        }
    }

    showMessageBox();


</script>
