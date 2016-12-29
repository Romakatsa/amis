<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="new_card">
    <form method="post" action="/addCard">
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
<div id="user_cards">
    <table>
        <tr>
            <th>#</th>
            <th>Card Name</th>
            <th>Card #</th>

            <th id="options_th">Options</th>
        </tr>
        <c:set var="row_count" value="1" scope="page" />
        <c:forEach items="${requestScope.cards}" var="card">
            <tr class="card">
                <td class="row_num">${count}</td>
                <td class="card_name">${card.getName()}</td>
                <td class="card_no">${card.getCardNo()}</td>

                <td class="delete_card"><a>delete</a></td>

            </tr>
            <c:set var="count" value="${count + 1}" scope="page"/>
        </c:forEach>
    </table>
</div>
















