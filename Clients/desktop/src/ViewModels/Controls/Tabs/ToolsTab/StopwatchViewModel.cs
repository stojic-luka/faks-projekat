using DesktopClient.src.Helpers;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows.Input;

namespace DesktopClient.src.ViewModels.Controls.Tabs.ToolsTab {
    internal class StopwatchViewModel : INotifyPropertyChanged {
        private readonly System.Timers.Timer _timer;
        private int _timeSeconds;
        private bool _isRunning;

        public int TimeSeconds {
            get { return _timeSeconds; }
            set {
                if (_timeSeconds != value) {
                    _timeSeconds = value;
                    OnPropertyChanged();

                    OnPropertyChanged(nameof(HourAngle));
                    OnPropertyChanged(nameof(MinuteAngle));
                    OnPropertyChanged(nameof(SecondAngle));
                    OnPropertyChanged(nameof(DisplayTime));
                }
            }
        }

        public bool IsRunning {
            get { return _isRunning; }
            private set {
                if (_isRunning != value) {
                    _isRunning = value;
                    OnPropertyChanged();
                }
            }
        }

        public double HourAngle => (TimeSeconds / 3600) % 12 * 30;
        public double MinuteAngle => (TimeSeconds / 60) % 60 * 6;
        public double SecondAngle => TimeSeconds % 60 * 6;
        public string DisplayTime {
            get {
                int hours = TimeSeconds / 3600;
                int minutes = (TimeSeconds % 3600) / 60;
                int seconds = TimeSeconds % 60;

                if (hours > 0)
                    return $"{hours:D2}:{minutes:D2}:{seconds:D2}";
                else
                    return $"{minutes:D2}:{seconds:D2}";
            }
        }

        public ICommand StartStopStopwatch { get; }
        public ICommand ResetStopwatch { get; }

        public StopwatchViewModel() {
            _timer = new System.Timers.Timer(1000);
            _timer.Elapsed += (sender, e) => TimeSeconds += 1;

            StartStopStopwatch = new RelayCommand(
                obj => {
                    if (!IsRunning) {
                        _timer.Start();
                        IsRunning = true;
                    } else {
                        _timer.Stop();
                        IsRunning = false;
                    }
                },
                obj => true
            );
            ResetStopwatch = new RelayCommand(
                obj => {
                    _timer.Stop();
                    IsRunning = false;
                    TimeSeconds = 0;
                },
                obj => true
            );
        }

        public void Start() {
            _timer.Start();
            IsRunning = true;
            System.Diagnostics.Trace.WriteLine("Start");
        }

        public void Stop() {
            _timer.Stop();
            IsRunning = false;
            System.Diagnostics.Trace.WriteLine("Stop");
        }

        public void Reset() {
            _timer.Stop();
            TimeSeconds = 0;
            System.Diagnostics.Trace.WriteLine("Reset");
        }

        public event PropertyChangedEventHandler? PropertyChanged;
        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = "") {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
