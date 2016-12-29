<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="new_payment">
    <form method="post" action="/pay">

        <div class="form-input">
            <label>Select phone</label><input type="text" id="phone_input" name="phone_input"><a id="phone-reset">reset</a>
            <div id="phones">
                <c:forEach items="${requestScope.phones}" var="phone">
                    <span value="${phone.getPhoneNumber()}">${phone.getPhoneNumber()} - ${phone.getPhoneName()}</span>
                </c:forEach>
            </div>
        </div>


        <label>Amount</label><input type="text" id="amount_input" name="amount_input">

        <div id="bank-card-select">
        <p>Select bank card</p>
        <select name="card_select">
            <option label=" "></option>
            <c:forEach items="${requestScope.cards}" var="card">
            <option value="${card.getCardNo()}">${card.getCardNo()} - ${card.getName()}</option>
            </c:forEach>
        </select>
            <a id="card-reset">reset</a>
        </div>

        <p>or input requisites</p>

        <div id="card_requisites">
            <div id="cards"><label>Card No</label><input type="text" id="card_input" name="card_input"></div>
            <label>Expire</label>
                <select id ="month_select" name="month_select">
                    <option value="1">01</option>
                    <option value="2">02</option>
                    <option value="3">03</option>
                    <option value="4">04</option>
                    <option value="5">05</option>
                    <option value="6">06</option>
                    <option value="7">07</option>
                    <option value="8">08</option>
                    <option value="9">09</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                </select>
                <select id="year_select" name="year_select">
                    <option value="16">16</option>
                    <option value="17">17</option>
                    <option value="18">18</option>
                </select>
            <label>CVV</label><input type="password" id="cvv_input" name="cvv_input">
        </div>

        <input type="submit">
    </form>
</div>
<div id="payments_history">
    <div id="filter">
        <h3>Filter by</h3>
        <span class="span-filter">phone number</span>
        <select name="phone-filter">
            <option label=" "></option>
            <c:forEach items="${requestScope.phones}" var="phone">
                <option value="${phone.getPhone()}">${phone.getPhone()} - ${phone.getName()}</option>
            </c:forEach>
        </select>
        <span class="span-filter">card number</span>
        <select name="card-filter">
            <option label=" "></option>
            <c:forEach items="${requestScope.cards}" var="card">
                <option value="${card.getCardNo()}">if="max-filter"${card.getCardNo()} - ${card.getName()}</option>
            </c:forEach>
        </select>
        <span class="span-filter">min amount</span>
        <input type="text" class="amount-filter" name="min-filter"/>
        <span class="span-filter">max amount</span>
        <input type="text" class="amount-filter" name="max-filter"/>
        <span class="span-filter">date from</span>
        <input type="date" class="date-filter" name="from-date">
        <span class="span-filter">till</span>
        <input type="date" class="date-filter" name="till-date">
        <button id="apply-filters">Apply filters</button>
        <button id="reset-filters">Reset filters</button>
    </div>
    <table>
        <tr>
            <th>#</th>
            <th>Phone</th>
            <th>Card Name</th>
            <th>Card #</th>
            <th>Amount</th>
            <th>Date</th>

        </tr>
        <c:set var="row_count" value="1" scope="page" />
    <c:forEach items="${requestScope.payments}" var="payment">
        <tr class="payment">
            <td class="row_num">${count}</td>
            <td class="payment_phone">${payment.getPhone()}</td>
            <td class="payment_CardName">${payment.getCard().getName()}</td>
            <td class="payment_CardNo">${payment.getCard().getCardNo()}</td>
            <td class="payment_amount">${payment.getAmount()}</td>
            <td class="payment_amount">${payment.getDate()}</td>
            <input type="hidden" name="payment_id" value="${payment.getID()}">
        </tr>
        <c:set var="count" value="${count + 1}" scope="page"/>
    </c:forEach>
    </table>
</div>