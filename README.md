# Account Manager Information System

---

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0) ![](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white) ![](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)

### 1. Requirement

#### 1.1 Application configuration

* Modify `amis.*` to fit your requirements.
* By default, This project relays on MariaDB instead of MySQL, Using other driver may lead to compatibility issue.
* Database default username: `root`, password: `dbpasswd`
* Change `spring.sql.init.mode` to avoid database reinitialization at every startup.
* Change `spring.web.resources.cache.period` to avoid cache-invalid performance issue.
* Change `spring.servlet.multipart.*` to modify max attachment size.

### 2. About Front-end resources

The Front-end webpages of this project is provided by others. This Project is RESTful style and
DO NOT require any of template engine like `jsp` or `thymeleaf`.

I'm not very into modifying look and logic of Front-end, so I just fit the provided mock pages to be
using Ajax.

I give up my copyright to all modified part to `.html` files but keep copyright to some of js files I wrote:
`add.js`, `base_table.js`, `update.js`, `utils.js`

Also, this project contains some codes from internet which released under their licenses.

**All code I declared copyright is released under GPL-3 License.**

### 3. TODO List (Will never be done)

- Using CDN to store attachment.
- Implement password retrieving using Q/A.
- Using Redis to share sessions across cluster.
- Improve Database performance and transaction.
- Improve UI logic.