<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            position: relative;
            overflow: hidden;
        }

        /* 배경 애니메이션 */
        .bg-animation {
            position: absolute;
            width: 100%;
            height: 100%;
            overflow: hidden;
            z-index: 0;
        }

        .bg-animation::before {
            content: '';
            position: absolute;
            width: 200px;
            height: 200px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 50%;
            animation: float 6s ease-in-out infinite;
            top: 10%;
            left: 10%;
        }

        .bg-animation::after {
            content: '';
            position: absolute;
            width: 150px;
            height: 150px;
            background: rgba(255, 255, 255, 0.05);
            border-radius: 50%;
            animation: float 8s ease-in-out infinite reverse;
            bottom: 10%;
            right: 10%;
        }

        @keyframes float {
            0%, 100% { transform: translateY(0px) rotate(0deg); }
            50% { transform: translateY(-20px) rotate(180deg); }
        }

        .login-container {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(20px);
            border-radius: 24px;
            padding: 40px;
            width: 100%;
            max-width: 420px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.2);
            position: relative;
            z-index: 1;
            border: 1px solid rgba(255, 255, 255, 0.3);
            animation: slideUp 0.8s ease-out;
        }

        @keyframes slideUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .login-header {
            text-align: center;
            margin-bottom: 40px;
        }

        .login-title {
            font-size: 32px;
            font-weight: 700;
            color: #2d3748;
            margin-bottom: 8px;
            background: linear-gradient(135deg, #667eea, #764ba2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .login-subtitle {
            color: #718096;
            font-size: 16px;
            font-weight: 400;
        }

        .form-group {
            margin-bottom: 24px;
            position: relative;
        }

        .form-label {
            display: block;
            font-weight: 600;
            color: #4a5568;
            margin-bottom: 8px;
            font-size: 14px;
            transition: color 0.3s ease;
        }

        .form-input {
            width: 100%;
            padding: 16px 20px;
            border: 2px solid #e2e8f0;
            border-radius: 12px;
            font-size: 16px;
            transition: all 0.3s ease;
            background: #fff;
            outline: none;
        }

        .form-input:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
            transform: translateY(-1px);
        }

        .form-input:focus + .form-label {
            color: #667eea;
        }

        .password-container {
            position: relative;
        }

        .password-toggle {
            position: absolute;
            right: 16px;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            color: #a0aec0;
            cursor: pointer;
            font-size: 18px;
            transition: color 0.3s ease;
        }

        .password-toggle:hover {
            color: #667eea;
        }

        .forgot-password {
            text-align: right;
            margin-bottom: 32px;
        }

        .forgot-password a {
            color: #667eea;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .forgot-password a:hover {
            color: #764ba2;
        }

        .login-btn {
            width: 100%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            padding: 16px;
            border-radius: 12px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .login-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(102, 126, 234, 0.4);
        }

        .login-btn:active {
            transform: translateY(0);
        }

        .login-btn::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255,255,255,0.3), transparent);
            transition: left 0.5s;
        }

        .login-btn:hover::before {
            left: 100%;
        }

        .divider {
            text-align: center;
            margin: 32px 0;
            position: relative;
            color: #a0aec0;
            font-size: 14px;
        }

        .divider::before {
            content: '';
            position: absolute;
            top: 50%;
            left: 0;
            right: 0;
            height: 1px;
            background: #e2e8f0;
        }

        .divider span {
            background: rgba(255, 255, 255, 0.95);
            padding: 0 16px;
            position: relative;
        }

        .social-login {
            display: flex;
            gap: 16px;
            margin-bottom: 32px;
        }

        .social-btn {
            flex: 1;
            padding: 12px;
            border: 2px solid #e2e8f0;
            border-radius: 12px;
            background: #fff;
            cursor: pointer;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 20px;
        }

        .social-btn:hover {
            border-color: #cbd5e0;
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .signup-link {
            text-align: center;
            color: #718096;
            font-size: 14px;
        }

        .signup-link a {
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.3s ease;
        }

        .signup-link a:hover {
            color: #764ba2;
        }

        .loading {
            opacity: 0.7;
            pointer-events: none;
        }

        .loading .login-btn {
            background: #a0aec0;
        }

        @media (max-width: 480px) {
            .login-container {
                margin: 20px;
                padding: 30px 24px;
            }
            
            .login-title {
                font-size: 28px;
            }
        }
    </style>
</head>
<body>
    <div class="bg-animation"></div>
    
    <div class="login-container">
        <div class="login-header">
            <h1 class="login-title">환영합니다</h1>
            <p class="login-subtitle">계정에 로그인하세요</p>
        </div>

        <form id="loginForm" class="login-form">
            <div class="form-group">
                <label for="email" class="form-label">이메일</label>
                <input type="email" id="email" class="form-input" placeholder="example@email.com" required>
            </div>

            <div class="form-group">
                <label for="password" class="form-label">비밀번호</label>
                <div class="password-container">
                    <input type="password" id="password" class="form-input" placeholder="비밀번호를 입력하세요" required>
                    <button type="button" class="password-toggle" onclick="togglePassword()">👁️</button>
                </div>
            </div>

            <div class="forgot-password">
                <a href="#" onclick="showAlert('비밀번호 재설정 기능')">비밀번호를 잊으셨나요?</a>
            </div>

            <button type="submit" class="login-btn">로그인</button>
        </form>

        <div class="divider">
            <span>또는</span>
        </div>

        <div class="social-login">
            <button class="social-btn" onclick="showAlert('구글 로그인')">🌐</button>
            <button class="social-btn" onclick="showAlert('카카오 로그인')">💬</button>
            <button class="social-btn" onclick="showAlert('네이버 로그인')">🔍</button>
        </div>

        <div class="signup-link">
            계정이 없으신가요? <a href="#" onclick="showAlert('회원가입 페이지')">회원가입</a>
        </div>
    </div>

    <script>
        // 비밀번호 표시/숨기기
        function togglePassword() {
            const passwordInput = document.getElementById('password');
            const toggleBtn = document.querySelector('.password-toggle');
            
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                toggleBtn.textContent = '🙈';
            } else {
                passwordInput.type = 'password';
                toggleBtn.textContent = '👁️';
            }
        }

        // 폼 제출 처리
        document.getElementById('loginForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            
            if (!email || !password) {
                showAlert('모든 필드를 입력해주세요.');
                return;
            }

            // 로딩 상태
            document.body.classList.add('loading');
            
            // 로그인 시뮬레이션
            setTimeout(() => {
                document.body.classList.remove('loading');
                showAlert('로그인 성공! 환영합니다.');
            }, 1500);
        });

        // 알림 표시
        function showAlert(message) {
            alert(message);
        }

        // 입력 필드 애니메이션
        document.querySelectorAll('.form-input').forEach(input => {
            input.addEventListener('focus', function() {
                this.parentElement.classList.add('focused');
            });
            
            input.addEventListener('blur', function() {
                if (!this.value) {
                    this.parentElement.classList.remove('focused');
                }
            });
        });

        // 페이지 로드 애니메이션
        window.addEventListener('load', function() {
            document.querySelector('.login-container').style.animation = 'slideUp 0.8s ease-out';
        });
    </script>
</body>
</html>